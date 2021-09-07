def project_url = 'https://gitee.com/irvingsoft-sodo/sodo-platform.git'
node {
    def mvnHome
    stage('拉取代码') {
        checkout([$class: 'GitSCM',
         branches: [[name: '*/master']],
         extensions: [],
         userRemoteConfigs: [[url: "${project_url}"]]])
    }
    stage('Build') {
        echo 'Build'
    }
    stage('Results') {
        echo 'Results'
    }
}
