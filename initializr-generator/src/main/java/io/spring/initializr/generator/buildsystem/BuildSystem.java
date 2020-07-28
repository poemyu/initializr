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

package io.spring.initializr.generator.buildsystem;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.core.io.support.SpringFactoriesLoader;

import io.spring.initializr.generator.language.Language;
import io.spring.initializr.generator.language.SourceStructure;

/**
 * A build system that can be used by a generated project.
 *
 * @author Andy Wilkinson
 */
public interface BuildSystem {

	/**
	 * The id of the build system.
	 * @return the id
	 */
	String id();

	/**
	 * The dialect of the build system, or {@code null} if the build system does not
	 * support multiple dialects.
	 * @return the dialect or {@code null}
	 */
	default String dialect() {
		return null;
	}

	/**
	 * Returns a {@link SourceStructure} for main sources.
	 * @param projectRoot the root of the project structure
	 * @param language the language of the project
	 * @return a {@link SourceStructure} for main assets
	 */
	default SourceStructure getMainSource(Path projectRoot, Language language) {
		return new SourceStructure(projectRoot.resolve("src/main/"), language);
	}

	/**
	 * Returns a {@link SourceStructure} for test sources.
	 * @param projectRoot the root of the project structure
	 * @param language the language of the project
	 * @return a {@link SourceStructure} for test assets
	 */
	default SourceStructure getTestSource(Path projectRoot, Language language) {
		return new SourceStructure(projectRoot.resolve("src/test/"), language);
	}

	default List<Path> getPathDirectory(Path projectRoot, Language language, String packageName) {
		List<Path> paths = new ArrayList<>();
		packageName = "/" + packageName.replaceAll("\\.", "\\/");
		paths.add(projectRoot.resolve("src/main/" + language.id() + packageName + "/comm/config"));
		paths.add(projectRoot.resolve("src/main/" + language.id() + packageName + "/controller"));
		paths.add(projectRoot.resolve("src/main/" + language.id() + packageName + "/entity"));
		paths.add(projectRoot.resolve("src/main/" + language.id() + packageName + "/dao"));
		paths.add(projectRoot.resolve("src/main/" + language.id() + packageName + "/repository"));
		paths.add(projectRoot.resolve("src/main/" + language.id() + packageName + "/service"));
		paths.add(projectRoot.resolve("src/main/" + language.id() + packageName + "/global"));
		paths.add(projectRoot.resolve("src/main/" + language.id() + packageName + "/global/cache"));
		paths.add(projectRoot.resolve("src/main/" + language.id() + packageName + "/global/constant"));
		paths.add(projectRoot.resolve("src/main/" + language.id() + packageName + "/global/enums"));
		paths.add(projectRoot.resolve("src/main/" + language.id() + packageName + "/rest/request"));
		paths.add(projectRoot.resolve("src/main/" + language.id() + packageName + "/rest/response"));
		paths.add(projectRoot.resolve("src/main/" + language.id() + packageName + "/utils"));
		paths.add(projectRoot.resolve("src/main/resources/mappers"));

		return paths;
	}

	default Map<String, Path> getFileDirectory(Path projectRoot, Language language, String packageName) {
		packageName = "/" + packageName.replaceAll("\\.", "\\/");
		HashMap<String, Path> m = new HashMap<>();

		m.put("classpath:configuration/RespCode.java",
				projectRoot.resolve("src/main/" + language.id() + packageName + "/comm/RespCode.java"));

		m.put("classpath:configuration/generatorConfig.xml",
				projectRoot.resolve("src/main/resources/generatorConfig.xml"));

		m.put("classpath:configuration/generatorConfigTk.xml",
				projectRoot.resolve("src/main/resources/generatorConfigTk.xml"));

		m.put("classpath:configuration/Logback.xml", projectRoot.resolve("src/main/resources/Logback.xml"));

		m.put("classpath:configuration/mybatisGeneratorinit.properties",
				projectRoot.resolve("src/main/resources/mybatisGeneratorinit.properties"));

		// m.put("classpath:configuration/Generator.java",
		// projectRoot.resolve("src/test/" + language.id() + packageName +
		// "/Generator.java"));

		return m;
	}

	static BuildSystem forId(String id) {
		return forIdAndDialect(id, null);
	}

	static BuildSystem forIdAndDialect(String id, String dialect) {
		return SpringFactoriesLoader.loadFactories(BuildSystemFactory.class, BuildSystem.class.getClassLoader())
				.stream().map((factory) -> factory.createBuildSystem(id, dialect)).filter(Objects::nonNull).findFirst()
				.orElseThrow(() -> new IllegalStateException(
						"Unrecognized build system id '" + id + "' and dialect '" + dialect + "'"));
	}

}
