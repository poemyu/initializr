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

package io.spring.initializr.web.support;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.spring.initializr.generator.version.Version;
import io.spring.initializr.generator.version.Version.Qualifier;
import io.spring.initializr.generator.version.VersionParser;
import io.spring.initializr.metadata.DefaultMetadataElement;

/**
 * Reads metadata from the main spring.io website. This is a stateful service: create a
 * new instance whenever you need to refresh the content.
 *
 * @author Stephane Nicoll
 */
class SpringBootMetadataReader {

	private final JsonNode content;

	/**
	 * Parse the content of the metadata at the specified url.
	 * @param objectMapper the object mapper
	 * @param restTemplate the rest template
	 * @param url the metadata URL
	 * @throws IOException on load error
	 */
	SpringBootMetadataReader(ObjectMapper objectMapper, RestTemplate restTemplate, String url) throws IOException {
		// this.content = objectMapper.readTree(restTemplate.getForObject(url,
		// String.class));
		this.content = objectMapper.readTree("{\r\n" + "          \"id\": \"spring-boot\",\r\n"
				+ "          \"name\": \"Spring Boot\",\r\n"
				+ "          \"repoUrl\": \"https://github.com/spring-projects/spring-boot\",\r\n"
				+ "          \"siteUrl\": \"https://spring.io/projects/spring-boot\",\r\n"
				+ "          \"category\": \"active\",\r\n" + "          \"rawBootConfig\": [],\r\n"
				+ "          \"renderedBootConfig\": [],\r\n"
				+ "          \"tagLine\": \"Takes an opinionated view of building Spring applications and gets you up and running as quickly as possible.\",\r\n"
				+ "          \"featured\": \"false\",\r\n"
				+ "          \"rawOverview\": \"Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can \\\"just run\\\". We take an opinionated view of the Spring platform and third-party libraries so you can get started with minimum fuss. Most Spring Boot applications need minimal Spring configuration. == Features * Create stand-alone Spring applications * Embed Tomcat, Jetty or Undertow directly (no need to deploy WAR files) * Provide opinionated 'starter' dependencies to simplify your build configuration * Automatically configure Spring and 3rd party libraries whenever possible * Provide production-ready features such as metrics, health checks, and externalized configuration * Absolutely no code generation and no requirement for XML configuration == Getting Started * Super quick — try the https://spring.io/quickstart[Quickstart Guide]. * More general — try https://spring.io/guides/gs/spring-boot/[Building an Application with Spring Boot] * More specific — try https://spring.io/guides/gs/rest-service/[Building a RESTful Web Service]. * Or search through all our guides on the https://spring.io/guides[Guides] homepage. == Talks and videos * https://content.pivotal.io/springone-platform-2017/its-a-kind-of-magic-under-the-covers-of-spring-boot-brian-clozel-st%C3%A9phane-nicoll[It’s a Kind of Magic: Under the Covers of Spring Boot] * https://content.pivotal.io/springone-platform-2017/whats-new-in-spring-boot-2-0-phillip-webb-madhura-bhave[What’s New in Spring Boot 2.0] * https://content.pivotal.io/webinars/mar-13-introducing-spring-boot-2-0-webinar[Introducing Spring Boot 2.0 webinar] * https://content.pivotal.io/springone-platform-2017/test-driven-development-with-spring-boot-sannidhi-jalukar-madhura-bhave[Test Driven Development with Spring Boot] * https://content.pivotal.io/springone-platform-2017/from-zero-to-hero-with-spring-boot-brian-clozel[From Zero to Hero with Spring Boot 2.0] You can also join the https://gitter.im/spring-projects/spring-boot[Spring Boot community on Gitter]!\",\r\n"
				+ "          \"renderedOverview\":           [\r\n"
				+ "                    [\"Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can \\\"just run\\\".\"],\r\n"
				+ "                    [\"We take an opinionated view of the Spring platform and third-party libraries so you can get started with minimum fuss. Most Spring Boot applications need minimal Spring configuration.\"],\r\n"
				+ "                                        {\r\n"
				+ "                              \"h2\":                               {\r\n"
				+ "                                        \"@id\": \"features\",\r\n"
				+ "                                        \"a\": {\"@href\": \"#features\"},\r\n"
				+ "                                        \"#text\": \"Features\"\r\n"
				+ "                              },\r\n"
				+ "                              \"div\": [[                              [\r\n"
				+ "                                        [\"Create stand-alone Spring applications\"],\r\n"
				+ "                                        [\"Embed Tomcat, Jetty or Undertow directly (no need to deploy WAR files)\"],\r\n"
				+ "                                        [\"Provide opinionated 'starter' dependencies to simplify your build configuration\"],\r\n"
				+ "                                        [\"Automatically configure Spring and 3rd party libraries whenever possible\"],\r\n"
				+ "                                        [\"Provide production-ready features such as metrics, health checks, and externalized configuration\"],\r\n"
				+ "                                        [\"Absolutely no code generation and no requirement for XML configuration\"]\r\n"
				+ "                              ]]]\r\n" + "                    },\r\n"
				+ "                                        {\r\n"
				+ "                              \"h2\":                               {\r\n"
				+ "                                        \"@id\": \"getting-started\",\r\n"
				+ "                                        \"a\": {\"@href\": \"#getting-started\"},\r\n"
				+ "                                        \"#text\": \"Getting Started\"\r\n"
				+ "                              },\r\n"
				+ "                              \"div\": [[                              [\r\n"
				+ "                                        [                                        {\r\n"
				+ "                                                  \"#text\":                                                   [\r\n"
				+ "                                                            \"Super quick — try the \",\r\n"
				+ "                                                            \".\"\r\n"
				+ "                                                  ],\r\n"
				+ "                                                  \"a\":                                                   {\r\n"
				+ "                                                            \"@href\": \"https://spring.io/quickstart\",\r\n"
				+ "                                                            \"#text\": \"Quickstart Guide\"\r\n"
				+ "                                                  }\r\n"
				+ "                                        }],\r\n"
				+ "                                        [                                        {\r\n"
				+ "                                                  \"#text\": \"More general — try \",\r\n"
				+ "                                                  \"a\":                                                   {\r\n"
				+ "                                                            \"@href\": \"https://spring.io/guides/gs/spring-boot/\",\r\n"
				+ "                                                            \"#text\": \"Building an Application with Spring Boot\"\r\n"
				+ "                                                  }\r\n"
				+ "                                        }],\r\n"
				+ "                                        [                                        {\r\n"
				+ "                                                  \"#text\":                                                   [\r\n"
				+ "                                                            \"More specific — try \",\r\n"
				+ "                                                            \".\"\r\n"
				+ "                                                  ],\r\n"
				+ "                                                  \"a\":                                                   {\r\n"
				+ "                                                            \"@href\": \"https://spring.io/guides/gs/rest-service/\",\r\n"
				+ "                                                            \"#text\": \"Building a RESTful Web Service\"\r\n"
				+ "                                                  }\r\n"
				+ "                                        }],\r\n"
				+ "                                        [                                        {\r\n"
				+ "                                                  \"#text\":                                                   [\r\n"
				+ "                                                            \"Or search through all our guides on the \",\r\n"
				+ "                                                            \" homepage.\"\r\n"
				+ "                                                  ],\r\n"
				+ "                                                  \"a\":                                                   {\r\n"
				+ "                                                            \"@href\": \"https://spring.io/guides\",\r\n"
				+ "                                                            \"#text\": \"Guides\"\r\n"
				+ "                                                  }\r\n"
				+ "                                        }]\r\n" + "                              ]]]\r\n"
				+ "                    },\r\n" + "                                        {\r\n"
				+ "                              \"h2\":                               {\r\n"
				+ "                                        \"@id\": \"talks-and-videos\",\r\n"
				+ "                                        \"a\": {\"@href\": \"#talks-and-videos\"},\r\n"
				+ "                                        \"#text\": \"Talks and videos\"\r\n"
				+ "                              },\r\n"
				+ "                              \"div\":                               [\r\n"
				+ "                                        [                                        [\r\n"
				+ "                                                  [{\"a\":                                                   {\r\n"
				+ "                                                            \"@href\": \"https://content.pivotal.io/springone-platform-2017/its-a-kind-of-magic-under-the-covers-of-spring-boot-brian-clozel-st%C3%A9phane-nicoll\",\r\n"
				+ "                                                            \"#text\": \"It’s a Kind of Magic: Under the Covers of Spring Boot\"\r\n"
				+ "                                                  }}],\r\n"
				+ "                                                  [{\"a\":                                                   {\r\n"
				+ "                                                            \"@href\": \"https://content.pivotal.io/springone-platform-2017/whats-new-in-spring-boot-2-0-phillip-webb-madhura-bhave\",\r\n"
				+ "                                                            \"#text\": \"What’s New in Spring Boot 2.0\"\r\n"
				+ "                                                  }}],\r\n"
				+ "                                                  [{\"a\":                                                   {\r\n"
				+ "                                                            \"@href\": \"https://content.pivotal.io/webinars/mar-13-introducing-spring-boot-2-0-webinar\",\r\n"
				+ "                                                            \"#text\": \"Introducing Spring Boot 2.0 webinar\"\r\n"
				+ "                                                  }}],\r\n"
				+ "                                                  [{\"a\":                                                   {\r\n"
				+ "                                                            \"@href\": \"https://content.pivotal.io/springone-platform-2017/test-driven-development-with-spring-boot-sannidhi-jalukar-madhura-bhave\",\r\n"
				+ "                                                            \"#text\": \"Test Driven Development with Spring Boot\"\r\n"
				+ "                                                  }}],\r\n"
				+ "                                                  [{\"a\":                                                   {\r\n"
				+ "                                                            \"@href\": \"https://content.pivotal.io/springone-platform-2017/from-zero-to-hero-with-spring-boot-brian-clozel\",\r\n"
				+ "                                                            \"#text\": \"From Zero to Hero with Spring Boot 2.0\"\r\n"
				+ "                                                  }}]\r\n"
				+ "                                        ]],\r\n"
				+ "                                        [                                        {\r\n"
				+ "                                                  \"#text\":                                                   [\r\n"
				+ "                                                            \"You can also join the \",\r\n"
				+ "                                                            \"!\"\r\n"
				+ "                                                  ],\r\n"
				+ "                                                  \"a\":                                                   {\r\n"
				+ "                                                            \"@href\": \"https://gitter.im/spring-projects/spring-boot\",\r\n"
				+ "                                                            \"#text\": \"Spring Boot community on Gitter\"\r\n"
				+ "                                                  }\r\n"
				+ "                                        }]\r\n" + "                              ]\r\n"
				+ "                    }\r\n" + "          ],\r\n" + "          \"displayOrder\": \"1\",\r\n"
				+ "          \"imagePath\": \"/images/projects/spring-boot.svg\",\r\n" + "          \"groups\": [],\r\n"
				+ "          \"childProjectList\": [],\r\n" + "          \"stackOverflowTags\": \"spring-boot\",\r\n"
				+ "          \"projectReleases\":           [\r\n" + "                                        {\r\n"
				+ "                              \"releaseStatus\": \"SNAPSHOT\",\r\n"
				+ "                              \"refDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.4.0-SNAPSHOT/reference/html/\",\r\n"
				+ "                              \"apiDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.4.0-SNAPSHOT/api/\",\r\n"
				+ "                              \"groupId\": \"org.springframework.boot\",\r\n"
				+ "                              \"artifactId\": \"spring-boot\",\r\n"
				+ "                              \"repository\":                               {\r\n"
				+ "                                        \"id\": \"spring-snapshots\",\r\n"
				+ "                                        \"name\": \"Spring Snapshots\",\r\n"
				+ "                                        \"url\": \"https://repo.spring.io/libs-snapshot\",\r\n"
				+ "                                        \"snapshotsEnabled\": \"true\"\r\n"
				+ "                              },\r\n"
				+ "                              \"version\": \"2.4.0-SNAPSHOT\",\r\n"
				+ "                              \"snapshot\": \"true\",\r\n"
				+ "                              \"versionDisplayName\": \"2.4\",\r\n"
				+ "                              \"generalAvailability\": \"false\",\r\n"
				+ "                              \"preRelease\": \"false\",\r\n"
				+ "                              \"current\": \"false\"\r\n" + "                    },\r\n"
				+ "                                        {\r\n"
				+ "                              \"releaseStatus\": \"PRERELEASE\",\r\n"
				+ "                              \"refDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.4.0-M1/reference/html/\",\r\n"
				+ "                              \"apiDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.4.0-M1/api/\",\r\n"
				+ "                              \"groupId\": \"org.springframework.boot\",\r\n"
				+ "                              \"artifactId\": \"spring-boot\",\r\n"
				+ "                              \"repository\":                               {\r\n"
				+ "                                        \"id\": \"spring-milestones\",\r\n"
				+ "                                        \"name\": \"Spring Milestones\",\r\n"
				+ "                                        \"url\": \"https://repo.spring.io/libs-milestone\",\r\n"
				+ "                                        \"snapshotsEnabled\": \"false\"\r\n"
				+ "                              },\r\n"
				+ "                              \"version\": \"2.4.0-M1\",\r\n"
				+ "                              \"snapshot\": \"false\",\r\n"
				+ "                              \"versionDisplayName\": \"2.4 0-M1\",\r\n"
				+ "                              \"generalAvailability\": \"false\",\r\n"
				+ "                              \"preRelease\": \"true\",\r\n"
				+ "                              \"current\": \"false\"\r\n" + "                    },\r\n"
				+ "                                        {\r\n"
				+ "                              \"releaseStatus\": \"SNAPSHOT\",\r\n"
				+ "                              \"refDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.3.3.BUILD-SNAPSHOT/reference/html/\",\r\n"
				+ "                              \"apiDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.3.3.BUILD-SNAPSHOT/api/\",\r\n"
				+ "                              \"groupId\": \"org.springframework.boot\",\r\n"
				+ "                              \"artifactId\": \"spring-boot\",\r\n"
				+ "                              \"repository\":                               {\r\n"
				+ "                                        \"id\": \"spring-snapshots\",\r\n"
				+ "                                        \"name\": \"Spring Snapshots\",\r\n"
				+ "                                        \"url\": \"https://repo.spring.io/libs-snapshot\",\r\n"
				+ "                                        \"snapshotsEnabled\": \"true\"\r\n"
				+ "                              },\r\n"
				+ "                              \"version\": \"2.3.3.BUILD-SNAPSHOT\",\r\n"
				+ "                              \"snapshot\": \"true\",\r\n"
				+ "                              \"versionDisplayName\": \"2.3.3\",\r\n"
				+ "                              \"generalAvailability\": \"false\",\r\n"
				+ "                              \"preRelease\": \"false\",\r\n"
				+ "                              \"current\": \"false\"\r\n" + "                    },\r\n"
				+ "                                        {\r\n"
				+ "                              \"releaseStatus\": \"GENERAL_AVAILABILITY\",\r\n"
				+ "                              \"refDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.3.2.RELEASE/reference/html/\",\r\n"
				+ "                              \"apiDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.3.2.RELEASE/api/\",\r\n"
				+ "                              \"groupId\": \"org.springframework.boot\",\r\n"
				+ "                              \"artifactId\": \"spring-boot\",\r\n"
				+ "                              \"version\": \"2.3.2.RELEASE\",\r\n"
				+ "                              \"snapshot\": \"false\",\r\n"
				+ "                              \"versionDisplayName\": \"2.3.2\",\r\n"
				+ "                              \"generalAvailability\": \"true\",\r\n"
				+ "                              \"preRelease\": \"false\",\r\n"
				+ "                              \"current\": \"true\"\r\n" + "                    },\r\n"
				+ "                                        {\r\n"
				+ "                              \"releaseStatus\": \"SNAPSHOT\",\r\n"
				+ "                              \"refDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.2.10.BUILD-SNAPSHOT/reference/html/\",\r\n"
				+ "                              \"apiDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.2.10.BUILD-SNAPSHOT/api/\",\r\n"
				+ "                              \"groupId\": \"org.springframework.boot\",\r\n"
				+ "                              \"artifactId\": \"spring-boot\",\r\n"
				+ "                              \"repository\":                               {\r\n"
				+ "                                        \"id\": \"spring-snapshots\",\r\n"
				+ "                                        \"name\": \"Spring Snapshots\",\r\n"
				+ "                                        \"url\": \"https://repo.spring.io/libs-snapshot\",\r\n"
				+ "                                        \"snapshotsEnabled\": \"true\"\r\n"
				+ "                              },\r\n"
				+ "                              \"version\": \"2.2.10.BUILD-SNAPSHOT\",\r\n"
				+ "                              \"snapshot\": \"true\",\r\n"
				+ "                              \"versionDisplayName\": \"2.2.10\",\r\n"
				+ "                              \"generalAvailability\": \"false\",\r\n"
				+ "                              \"preRelease\": \"false\",\r\n"
				+ "                              \"current\": \"false\"\r\n" + "                    },\r\n"
				+ "                                        {\r\n"
				+ "                              \"releaseStatus\": \"GENERAL_AVAILABILITY\",\r\n"
				+ "                              \"refDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.2.9.RELEASE/reference/html/\",\r\n"
				+ "                              \"apiDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.2.9.RELEASE/api/\",\r\n"
				+ "                              \"groupId\": \"org.springframework.boot\",\r\n"
				+ "                              \"artifactId\": \"spring-boot\",\r\n"
				+ "                              \"version\": \"2.2.9.RELEASE\",\r\n"
				+ "                              \"snapshot\": \"false\",\r\n"
				+ "                              \"versionDisplayName\": \"2.2.9\",\r\n"
				+ "                              \"generalAvailability\": \"true\",\r\n"
				+ "                              \"preRelease\": \"false\",\r\n"
				+ "                              \"current\": \"false\"\r\n" + "                    },\r\n"
				+ "                                        {\r\n"
				+ "                              \"releaseStatus\": \"SNAPSHOT\",\r\n"
				+ "                              \"refDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.1.17.BUILD-SNAPSHOT/reference/html/\",\r\n"
				+ "                              \"apiDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.1.17.BUILD-SNAPSHOT/api/\",\r\n"
				+ "                              \"groupId\": \"org.springframework.boot\",\r\n"
				+ "                              \"artifactId\": \"spring-boot\",\r\n"
				+ "                              \"repository\":                               {\r\n"
				+ "                                        \"id\": \"spring-snapshots\",\r\n"
				+ "                                        \"name\": \"Spring Snapshots\",\r\n"
				+ "                                        \"url\": \"https://repo.spring.io/libs-snapshot\",\r\n"
				+ "                                        \"snapshotsEnabled\": \"true\"\r\n"
				+ "                              },\r\n"
				+ "                              \"version\": \"2.1.17.BUILD-SNAPSHOT\",\r\n"
				+ "                              \"snapshot\": \"true\",\r\n"
				+ "                              \"versionDisplayName\": \"2.1.17\",\r\n"
				+ "                              \"generalAvailability\": \"false\",\r\n"
				+ "                              \"preRelease\": \"false\",\r\n"
				+ "                              \"current\": \"false\"\r\n" + "                    },\r\n"
				+ "                                        {\r\n"
				+ "                              \"releaseStatus\": \"GENERAL_AVAILABILITY\",\r\n"
				+ "                              \"refDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.1.16.RELEASE/reference/html/\",\r\n"
				+ "                              \"apiDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.1.16.RELEASE/api/\",\r\n"
				+ "                              \"groupId\": \"org.springframework.boot\",\r\n"
				+ "                              \"artifactId\": \"spring-boot\",\r\n"
				+ "                              \"version\": \"2.1.16.RELEASE\",\r\n"
				+ "                              \"snapshot\": \"false\",\r\n"
				+ "                              \"versionDisplayName\": \"2.1.16\",\r\n"
				+ "                              \"generalAvailability\": \"true\",\r\n"
				+ "                              \"preRelease\": \"false\",\r\n"
				+ "                              \"current\": \"false\"\r\n" + "                    }\r\n"
				+ "          ],\r\n" + "          \"projectSamples\":           [\r\n"
				+ "                                        {\r\n"
				+ "                              \"title\": \"Sagan\",\r\n"
				+ "                              \"description\": \"The Spring Boot application behind the spring.io website\",\r\n"
				+ "                              \"url\": \"https://github.com/spring-io/sagan\",\r\n"
				+ "                              \"displayOrder\": \"1\"\r\n" + "                    },\r\n"
				+ "                                        {\r\n"
				+ "                              \"title\": \"Initializr\",\r\n"
				+ "                              \"description\": \"start.spring.io, powered by Spring Boot\",\r\n"
				+ "                              \"url\": \"https://github.com/spring-io/initializr\",\r\n"
				+ "                              \"displayOrder\": \"2\"\r\n" + "                    }\r\n"
				+ "          ],\r\n" + "          \"mostCurrentRelease\": {\"present\": \"true\"},\r\n"
				+ "          \"nonMostCurrentReleases\":           [\r\n"
				+ "                                        {\r\n"
				+ "                              \"releaseStatus\": \"SNAPSHOT\",\r\n"
				+ "                              \"refDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.4.0-SNAPSHOT/reference/html/\",\r\n"
				+ "                              \"apiDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.4.0-SNAPSHOT/api/\",\r\n"
				+ "                              \"groupId\": \"org.springframework.boot\",\r\n"
				+ "                              \"artifactId\": \"spring-boot\",\r\n"
				+ "                              \"repository\":                               {\r\n"
				+ "                                        \"id\": \"spring-snapshots\",\r\n"
				+ "                                        \"name\": \"Spring Snapshots\",\r\n"
				+ "                                        \"url\": \"https://repo.spring.io/libs-snapshot\",\r\n"
				+ "                                        \"snapshotsEnabled\": \"true\"\r\n"
				+ "                              },\r\n"
				+ "                              \"version\": \"2.4.0-SNAPSHOT\",\r\n"
				+ "                              \"snapshot\": \"true\",\r\n"
				+ "                              \"versionDisplayName\": \"2.4\",\r\n"
				+ "                              \"generalAvailability\": \"false\",\r\n"
				+ "                              \"preRelease\": \"false\",\r\n"
				+ "                              \"current\": \"false\"\r\n" + "                    },\r\n"
				+ "                                        {\r\n"
				+ "                              \"releaseStatus\": \"PRERELEASE\",\r\n"
				+ "                              \"refDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.4.0-M1/reference/html/\",\r\n"
				+ "                              \"apiDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.4.0-M1/api/\",\r\n"
				+ "                              \"groupId\": \"org.springframework.boot\",\r\n"
				+ "                              \"artifactId\": \"spring-boot\",\r\n"
				+ "                              \"repository\":                               {\r\n"
				+ "                                        \"id\": \"spring-milestones\",\r\n"
				+ "                                        \"name\": \"Spring Milestones\",\r\n"
				+ "                                        \"url\": \"https://repo.spring.io/libs-milestone\",\r\n"
				+ "                                        \"snapshotsEnabled\": \"false\"\r\n"
				+ "                              },\r\n"
				+ "                              \"version\": \"2.4.0-M1\",\r\n"
				+ "                              \"snapshot\": \"false\",\r\n"
				+ "                              \"versionDisplayName\": \"2.4 0-M1\",\r\n"
				+ "                              \"generalAvailability\": \"false\",\r\n"
				+ "                              \"preRelease\": \"true\",\r\n"
				+ "                              \"current\": \"false\"\r\n" + "                    },\r\n"
				+ "                                        {\r\n"
				+ "                              \"releaseStatus\": \"SNAPSHOT\",\r\n"
				+ "                              \"refDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.3.3.BUILD-SNAPSHOT/reference/html/\",\r\n"
				+ "                              \"apiDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.3.3.BUILD-SNAPSHOT/api/\",\r\n"
				+ "                              \"groupId\": \"org.springframework.boot\",\r\n"
				+ "                              \"artifactId\": \"spring-boot\",\r\n"
				+ "                              \"repository\":                               {\r\n"
				+ "                                        \"id\": \"spring-snapshots\",\r\n"
				+ "                                        \"name\": \"Spring Snapshots\",\r\n"
				+ "                                        \"url\": \"https://repo.spring.io/libs-snapshot\",\r\n"
				+ "                                        \"snapshotsEnabled\": \"true\"\r\n"
				+ "                              },\r\n"
				+ "                              \"version\": \"2.3.3.BUILD-SNAPSHOT\",\r\n"
				+ "                              \"snapshot\": \"true\",\r\n"
				+ "                              \"versionDisplayName\": \"2.3.3\",\r\n"
				+ "                              \"generalAvailability\": \"false\",\r\n"
				+ "                              \"preRelease\": \"false\",\r\n"
				+ "                              \"current\": \"false\"\r\n" + "                    },\r\n"
				+ "                                        {\r\n"
				+ "                              \"releaseStatus\": \"SNAPSHOT\",\r\n"
				+ "                              \"refDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.2.10.BUILD-SNAPSHOT/reference/html/\",\r\n"
				+ "                              \"apiDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.2.10.BUILD-SNAPSHOT/api/\",\r\n"
				+ "                              \"groupId\": \"org.springframework.boot\",\r\n"
				+ "                              \"artifactId\": \"spring-boot\",\r\n"
				+ "                              \"repository\":                               {\r\n"
				+ "                                        \"id\": \"spring-snapshots\",\r\n"
				+ "                                        \"name\": \"Spring Snapshots\",\r\n"
				+ "                                        \"url\": \"https://repo.spring.io/libs-snapshot\",\r\n"
				+ "                                        \"snapshotsEnabled\": \"true\"\r\n"
				+ "                              },\r\n"
				+ "                              \"version\": \"2.2.10.BUILD-SNAPSHOT\",\r\n"
				+ "                              \"snapshot\": \"true\",\r\n"
				+ "                              \"versionDisplayName\": \"2.2.10\",\r\n"
				+ "                              \"generalAvailability\": \"false\",\r\n"
				+ "                              \"preRelease\": \"false\",\r\n"
				+ "                              \"current\": \"false\"\r\n" + "                    },\r\n"
				+ "                                        {\r\n"
				+ "                              \"releaseStatus\": \"GENERAL_AVAILABILITY\",\r\n"
				+ "                              \"refDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.2.9.RELEASE/reference/html/\",\r\n"
				+ "                              \"apiDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.2.9.RELEASE/api/\",\r\n"
				+ "                              \"groupId\": \"org.springframework.boot\",\r\n"
				+ "                              \"artifactId\": \"spring-boot\",\r\n"
				+ "                              \"version\": \"2.2.9.RELEASE\",\r\n"
				+ "                              \"snapshot\": \"false\",\r\n"
				+ "                              \"versionDisplayName\": \"2.2.9\",\r\n"
				+ "                              \"generalAvailability\": \"true\",\r\n"
				+ "                              \"preRelease\": \"false\",\r\n"
				+ "                              \"current\": \"false\"\r\n" + "                    },\r\n"
				+ "                                        {\r\n"
				+ "                              \"releaseStatus\": \"SNAPSHOT\",\r\n"
				+ "                              \"refDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.1.17.BUILD-SNAPSHOT/reference/html/\",\r\n"
				+ "                              \"apiDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.1.17.BUILD-SNAPSHOT/api/\",\r\n"
				+ "                              \"groupId\": \"org.springframework.boot\",\r\n"
				+ "                              \"artifactId\": \"spring-boot\",\r\n"
				+ "                              \"repository\":                               {\r\n"
				+ "                                        \"id\": \"spring-snapshots\",\r\n"
				+ "                                        \"name\": \"Spring Snapshots\",\r\n"
				+ "                                        \"url\": \"https://repo.spring.io/libs-snapshot\",\r\n"
				+ "                                        \"snapshotsEnabled\": \"true\"\r\n"
				+ "                              },\r\n"
				+ "                              \"version\": \"2.1.17.BUILD-SNAPSHOT\",\r\n"
				+ "                              \"snapshot\": \"true\",\r\n"
				+ "                              \"versionDisplayName\": \"2.1.17\",\r\n"
				+ "                              \"generalAvailability\": \"false\",\r\n"
				+ "                              \"preRelease\": \"false\",\r\n"
				+ "                              \"current\": \"false\"\r\n" + "                    },\r\n"
				+ "                                        {\r\n"
				+ "                              \"releaseStatus\": \"GENERAL_AVAILABILITY\",\r\n"
				+ "                              \"refDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.1.16.RELEASE/reference/html/\",\r\n"
				+ "                              \"apiDocUrl\": \"https://docs.spring.io/spring-boot/docs/2.1.16.RELEASE/api/\",\r\n"
				+ "                              \"groupId\": \"org.springframework.boot\",\r\n"
				+ "                              \"artifactId\": \"spring-boot\",\r\n"
				+ "                              \"version\": \"2.1.16.RELEASE\",\r\n"
				+ "                              \"snapshot\": \"false\",\r\n"
				+ "                              \"versionDisplayName\": \"2.1.16\",\r\n"
				+ "                              \"generalAvailability\": \"true\",\r\n"
				+ "                              \"preRelease\": \"false\",\r\n"
				+ "                              \"current\": \"false\"\r\n" + "                    }\r\n"
				+ "          ],\r\n"
				+ "          \"stackOverflowTagList\": {\"stackOverflowTagList\": \"spring-boot\"},\r\n"
				+ "          \"topLevelProject\": \"true\"\r\n" + "}");
	}

	/**
	 * Return the boot versions parsed by this instance.
	 * @return the versions
	 */
	List<DefaultMetadataElement> getBootVersions() {
		ArrayNode releases = (ArrayNode) this.content.get("projectReleases");
		List<DefaultMetadataElement> list = new ArrayList<>();
		for (JsonNode node : releases) {
			DefaultMetadataElement versionMetadata = parseVersionMetadata(node);
			if (versionMetadata != null) {
				list.add(versionMetadata);
			}
		}
		return list;
	}

	private DefaultMetadataElement parseVersionMetadata(JsonNode node) {
		String versionId = node.get("version").textValue();
		Version version = VersionParser.DEFAULT.safeParse(versionId);
		if (version == null) {
			return null;
		}
		DefaultMetadataElement versionMetadata = new DefaultMetadataElement();
		versionMetadata.setId(versionId);
		versionMetadata.setName(determineDisplayName(version));
		versionMetadata.setDefault(node.get("current").booleanValue());
		return versionMetadata;
	}

	private String determineDisplayName(Version version) {
		StringBuilder sb = new StringBuilder();
		sb.append(version.getMajor()).append(".").append(version.getMinor()).append(".").append(version.getPatch());
		if (version.getQualifier() != null) {
			sb.append(determineSuffix(version.getQualifier()));
		}
		return sb.toString();
	}

	private String determineSuffix(Qualifier qualifier) {
		String id = qualifier.getId();
		if (id.equals("RELEASE")) {
			return "";
		}
		StringBuilder sb = new StringBuilder(" (");
		if (id.contains("SNAPSHOT")) {
			sb.append("SNAPSHOT");
		}
		else {
			sb.append(id);
			if (qualifier.getVersion() != null) {
				sb.append(qualifier.getVersion());
			}
		}
		sb.append(")");
		return sb.toString();
	}

}
