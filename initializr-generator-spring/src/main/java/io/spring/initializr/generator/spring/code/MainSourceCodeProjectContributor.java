/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.initializr.generator.spring.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import io.spring.initializr.generator.language.Annotation;
import io.spring.initializr.generator.language.CompilationUnit;
import io.spring.initializr.generator.language.SourceCode;
import io.spring.initializr.generator.language.SourceCodeWriter;
import io.spring.initializr.generator.language.TypeDeclaration;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.generator.spring.util.FileTemplateUtil;
import io.spring.initializr.generator.spring.util.LambdaSafe;

/**
 * {@link ProjectContributor} for the application's main source code.
 *
 * @param <T> language-specific type declaration
 * @param <C> language-specific compilation unit
 * @param <S> language-specific source code
 * @author Andy Wilkinson
 * @author Stephane Nicoll
 */
public class MainSourceCodeProjectContributor<T extends TypeDeclaration, C extends CompilationUnit<T>, S extends SourceCode<T, C>>
		implements ProjectContributor {

	private final ProjectDescription description;

	private final Supplier<S> sourceFactory;

	private final SourceCodeWriter<S> sourceWriter;

	private final ObjectProvider<MainApplicationTypeCustomizer<? extends TypeDeclaration>> mainTypeCustomizers;

	private final ObjectProvider<MainCompilationUnitCustomizer<?, ?>> mainCompilationUnitCustomizers;

	private final ObjectProvider<MainSourceCodeCustomizer<?, ?, ?>> mainSourceCodeCustomizers;

	public MainSourceCodeProjectContributor(ProjectDescription description, Supplier<S> sourceFactory,
			SourceCodeWriter<S> sourceWriter, ObjectProvider<MainApplicationTypeCustomizer<?>> mainTypeCustomizers,
			ObjectProvider<MainCompilationUnitCustomizer<?, ?>> mainCompilationUnitCustomizers,
			ObjectProvider<MainSourceCodeCustomizer<?, ?, ?>> mainSourceCodeCustomizers) {
		this.description = description;
		this.sourceFactory = sourceFactory;
		this.sourceWriter = sourceWriter;
		this.mainTypeCustomizers = mainTypeCustomizers;
		this.mainCompilationUnitCustomizers = mainCompilationUnitCustomizers;
		this.mainSourceCodeCustomizers = mainSourceCodeCustomizers;
	}

	@Override
	public void contribute(Path projectRoot) throws IOException {
		S sourceCode = this.sourceFactory.get();
		String applicationName = this.description.getApplicationName();
		C compilationUnit = sourceCode.createCompilationUnit(this.description.getPackageName(), applicationName);
		T mainApplicationType = compilationUnit.createTypeDeclaration(applicationName);
		mainApplicationType.annotate(this.addCustomAnnotation(this.description.getPackageName()));
		customizeMainApplicationType(mainApplicationType);
		customizeMainCompilationUnit(compilationUnit);
		customizeMainSourceCode(sourceCode);
		this.sourceWriter.writeTo(
				this.description.getBuildSystem().getMainSource(projectRoot, this.description.getLanguage()),
				sourceCode);

		List<Path> paths = this.description.getBuildSystem().getPathDirectory(projectRoot,
				this.description.getLanguage(), this.description.getPackageName());
		for (Path directory : paths) {
			if (!Files.exists(directory)) {
				Files.createDirectories(directory);
			}
		}

		Map<String, Path> map = this.description.getBuildSystem().getFileDirectory(projectRoot,
				this.description.getLanguage(), this.description.getPackageName());

		for (Map.Entry<String, Path> entry : map.entrySet()) {
			String resourcePattern = entry.getKey();
			Path output = entry.getValue();
			if (!Files.exists(output)) {
				Files.createDirectories(output.getParent());
				Files.createFile(output);
			}
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource resource = resolver.getResource(resourcePattern);
			FileTemplateUtil.copy(resource.getInputStream(), Files.newOutputStream(output, StandardOpenOption.APPEND),
					this.description.getPackageName());
			// try (InputStream inputStream = resource.getInputStream()) {
			// Files.copy(inputStream, output, StandardCopyOption.REPLACE_EXISTING);
			// }

		}
	}

	private Annotation addCustomAnnotation(String packageName) {
		return Annotation.name("tk.mybatis.spring.annotation.MapperScan", (builder) -> builder.attribute("basePackages",
				String[].class, "\"" + packageName + ".dao\"", "\"" + packageName + ".dao.**\""));
	}

	@SuppressWarnings("unchecked")
	private void customizeMainApplicationType(T mainApplicationType) {
		List<MainApplicationTypeCustomizer<?>> customizers = this.mainTypeCustomizers.orderedStream()
				.collect(Collectors.toList());
		LambdaSafe.callbacks(MainApplicationTypeCustomizer.class, customizers, mainApplicationType)
				.invoke((customizer) -> customizer.customize(mainApplicationType));
	}

	@SuppressWarnings("unchecked")
	private void customizeMainCompilationUnit(C compilationUnit) {
		List<MainCompilationUnitCustomizer<?, ?>> customizers = this.mainCompilationUnitCustomizers.orderedStream()
				.collect(Collectors.toList());
		LambdaSafe.callbacks(MainCompilationUnitCustomizer.class, customizers, compilationUnit)
				.invoke((customizer) -> customizer.customize(compilationUnit));
	}

	@SuppressWarnings("unchecked")
	private void customizeMainSourceCode(S sourceCode) {
		List<MainSourceCodeCustomizer<?, ?, ?>> customizers = this.mainSourceCodeCustomizers.orderedStream()
				.collect(Collectors.toList());
		LambdaSafe.callbacks(MainSourceCodeCustomizer.class, customizers, sourceCode)
				.invoke((customizer) -> customizer.customize(sourceCode));
	}

}
