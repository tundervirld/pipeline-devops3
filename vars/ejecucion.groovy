def call(){
    pipeline {
        agent any
        environment {
            NEXUS_USER         = credentials('user-nexus')
            NEXUS_PASSWORD     = credentials('password-nexus')
        }
        parameters {
            choice  name: 'compileTool', choices: ['Gradle', 'Maven'], description: 'Seleccione el empaquetador maven/gradle'
        }
        stages {
            stage("Pipeline"){
                steps {
                    script{
                        // params.compileTool
                        sh "env"
                        switch(params.compileTool)
                        {
                            case 'Maven':
                                echo "Maven"
                                def ejecucion = load 'maven.groovy'
                                ejecucion.call()
                            break;
                            case 'Gradle':
                                def ejecucion = load 'gradle.groovy'
                                ejecucion.call()
                            break;
                        }
                    }
                }
                post {
                    always {
                        sh "echo 'fase always executed post'"
                    }

                    success {
                        sh "echo 'fase success'"
                    }

                    failure {
                        sh "echo 'fase failure'"
                    }
                }
            }
        }
    }
}