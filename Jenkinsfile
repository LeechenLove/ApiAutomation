pipeline {
    agent any

    options {
            timestamps()    // 构建日志中带上时间
            disableConcurrentBuilds()   // 不允许同时执行流水线
            timeout(time: 5, unit: "MINUTES")   // 设置流水线运行超过5分钟，Jenkins将中止流水线
            buildDiscarder(logRotator(numToKeepStr: "10"))   // 表示保留10次构建历史
    }
    // 每天23点自动构建
    triggers{cron('H 23 * * *')}

    parameters{
    choice(
                choices: ['test', 'uat'],
                description: '''构建选项：
                测试环境：test
                预发布环境：uat
                ''',
                name: 'ENV'
            )
    choice(
                choices: ['smoketest.xml', 'releasetest.xml'],
                description: '''测试用例集：
                冒烟测试用例集：test
                预发布测试用例集：uat
                ''',
                name: 'TESTSET'
            )
    }

    stages {
        stage("打印信息") {      //打印信息
                    steps {
                        echo "打印信息"
                        echo "jenkins任务名: ${JOB_NAME}"
                        echo "测试环境: ${ENV}"
                        echo "build_id: ${BUILD_ID}"
                        echo "工作空间: ${WORKSPACE}"
                   }
                }

        stage('checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: '3333', url: 'https://github.com/LeechenLove/ApiAutomation.git']]])
            }
        }

        stage('mvn build_id') {
            steps {
                bat 'mvn clean test -Dtestset=${TESTSET} -P${ENV}'
            }

            post {
               always {
                 step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])
               }
             }
        }
    }
}