def project_name_parent = "sodo-platform"
def harbor_url = "172.16.16.8:8081"
def harbor_project_name = "sodo-platform"
def harbor_auth = "Harbor"
def tag = "latest"
def image_name = "${project_name}:${tag}"

node {
    dir("/var/lib/jenkins/workspace/" + "${project_name_parent}" + "/") {
        stage('Build Project') {
            // Run the maven build and docker build.
            echo "build sodo-common 模块......."
            sh "mvn -f sodo-common/pom.xml -Dmaven.test.skip=true clean install package"
            if("${build_platform}" == "true") {
                echo "build sodo-platform 父模块......."
                sh "mvn -f pom.xml -Dmaven.test.skip=true clean install package"
            }
            echo "build ${project_name}......."
            sh "mvn -f ${project_name}/pom.xml -Dmaven.test.skip=true clean install package -P prod dockerfile:build"
            sh "docker tag ${image_name} ${harbor_url}/${harbor_project_name}/${image_name}"
        }
        stage('Upload Image') {
            // Upload the image to Harbor.
            withCredentials([
                    usernamePassword(
                        credentialsId: "${harbor_auth}",
                        passwordVariable: 'password',
                        usernameVariable: 'username'
                    )
                ]) {
                // Login Harbor
                sh "docker login -u ${username} -p ${password} ${harbor_url}"
                // Upload
                sh "docker push ${harbor_url}/${harbor_project_name}/${image_name}"
            }
        }
        stage('Delete Images') {
            // Delete local images which had been uploaded to Harbor.
            sh "docker rmi -f ${image_name}"
            sh "docker rmi -f ${harbor_url}/${harbor_project_name}/${image_name}"
        }
        stage('Remote Deploy') {
            // Deploy the image to the remote server.
            sshPublisher(
                publishers: [
                    sshPublisherDesc(
                        configName: 'Irvingsoft_Server',
                        transfers: [
                            sshTransfer(
                                cleanRemote: false,
                                excludes: '',
                                execCommand: "/root/docker-deploy.sh $harbor_url $harbor_project_name $project_name $tag $port",
                                execTimeout: 120000,
                                flatten: false,
                                makeEmptyDirs: false,
                                noDefaultExcludes: false,
                                patternSeparator: '[, ]+',
                                remoteDirectory: '',
                                remoteDirectorySDF: false,
                                removePrefix: '',
                                sourceFiles: ''
                            )
                        ],
                        usePromotionTimestamp: false,
                        useWorkspaceInPromotion: false,
                        verbose: true
                    )
                ]
            )
        }
    }
}