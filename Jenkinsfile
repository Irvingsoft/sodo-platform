def project_url = 'https://gitee.com/irvingsoft-sodo/sodo-platform.git'
node {
    def mvnHome
    stage('拉取代码') {
        checkout([$class: 'GitSCM',
         branches: [[name: '*/master']],
         extensions: [],
         userRemoteConfigs: [[url: "${project_url}"]]])
    }
    stage('构建') {
        // 安装 common 包脚本
        sh "mvn -f sodo-common/pom.xml clean install"
        sh "mvn -f ${project_name}/pom.xml clean package"
    }
    stage('Results') {
        echo 'Results'
    }
}
