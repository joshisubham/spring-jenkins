pipeline {
    agent any

    tools {
        jdk "openjdk-17"
        maven "Maven3"
    }

    stages { 
        stage('Fetch code') {
          steps{
              git branch: 'master', url:'https://github.com/joshisubham/spring-jenkins.git'
          }  
        }
        stage('Build') {
            steps {
                sh "mvn clean install -DskipTests"
            }
        }
        stage('Test'){
            steps {
                sh 'mvn test'
            }

        }
        stage('Checkstyle Analysis'){
            steps {
                sh 'mvn checkstyle:checkstyle'
            }
        }
        stage('Sonar Analysis') {
            environment {
                scannerHome = tool 'sonar6'
            }
            steps {
               withSonarQubeEnv('sonar') {
                   sh """
                    ${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=spring-jenkins \
                    -Dsonar.projectVersion=1.0 \
                    -Dsonar.language=java \
                    -Dsonar.sources=src/main/java \
                    -Dsonar.sourceEncoding=UTF-8 \
                    -Dsonar.junit.reportsPath=target/surefire-reports/ \
                    -Dsonar.jacoco.reportsPath=target/jacoco.exec \
                    -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml \
                    -Dsonar.java.binaries=.
                    """
              }
            }
            post {
                always {
                    // Record JaCoCo coverage report with defaults
                    jacoco()
                    junit 'target/surefire-reports/**/*.xml'
                }
                success {
                    // archiveArtifacts 'target/*.jar'
                    archiveArtifacts artifacts: '**/*.jar'
                }
            }
        }
        stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage("Publish to Nexus Repository Manager") {
            steps {
                script {
                    pom = readMavenPom file: "pom.xml";
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    artifactPath = filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                    
                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: 'localhost:8081',
                        groupId: 'QA',
                        version: "${env.BUILD_ID}",
                        repository: 'spring-jenkins-repo',
                        credentialsId: 'NexusLogin',
                        artifacts: [
                            [artifactId: 'spring-jenkins',
                            classifier: '',
                            file: artifactPath,
                            type: 'jar']
    ]
 )
                    }
                }
            }
        }
       
    }
    
}
