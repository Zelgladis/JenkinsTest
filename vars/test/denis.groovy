def call(){
    stage('🐛Deploy PP') {
        env.startDeployment_CONTOUR = 'PREPROD'
        env.startDeployment_VERSION = "${Version}"
        if ( env.NewHelm != 'true' ) {
            sh "rm inventory -rf; mkdir inventory"
            dir("inventory") {
                git branch: "${Version_inv}",
                        credentialsId: 'git_key',
                        url: "ssh://git@git.sfera.vtb.ru:7999/${globalSystem}_deploy/inventory.git"
            }
            dir("crypto") {
                git branch: 'master',
                        credentialsId: 'git_key',
                        url: "ssh://git@git.sfera.vtb.ru:7999/${globalSystem}_deploy/sferapipeline.git"
            }
            sh """
             mv "./crypto/crypt.sh" "."
             chmod +x crypt.sh
             ./crypt.sh decrypt -p ${vault_password} --path ${CONTOUR}
       """
            sh "rm supply -rf; mkdir supply"
            dir("supply") {
                git branch: "${Version_supply}",
                        credentialsId: 'git_key',
                        url: "ssh://git@git.sfera.vtb.ru:7999/${globalSystem}_deploy/supply.git"
            }
            withCredentials([string(credentialsId: "kuber_token", variable: 'TTOKEN')]) {
                DEPLOY_STATUS = sh(
                        script: """
                        #!/usr/bin/env bash
                        cp -R ./supply/* ./inventory/K4
                        ls
                        ${oc}/oc login --server=${k8s_cluster_api} --token="${TToken}" --insecure-skip-tls-verify
                        ${oc}/oc project "${k8s_namespace}"
                        ${helm}/helm upgrade -n ${k8s_namespace} -i --set global.version=${Version} ${JOB_BASE_NAME} ${CONTOUR} --values ${CONTOUR}/values.yaml --dry-run > /dev/null
                        ${helm}/helm upgrade -n ${k8s_namespace} -i --set global.version=${Version} ${JOB_BASE_NAME} ${CONTOUR} --values ${CONTOUR}/values.yaml --atomic
                        ${oc}/oc logout
                        """,
                        returnStatus: true
                )
            }
        } else {
            sh "rm inventory -rf; mkdir inventory"
            dir("inventory") {
                git branch: "${Version_inv}",
                        credentialsId: 'git_key',
                        url: "ssh://git@git.sfera.vtb.ru:7999/${globalSystem}_deploy/inventory-ah-${globalSystem}.git"
            }
            dir("crypto") {
                git branch: 'master',
                        credentialsId: 'git_key',
                        url: "ssh://git@git.sfera.vtb.ru:7999/${globalSystem}_deploy/sferapipeline.git"
            }
            sh """
             mv "./crypto/crypt.sh" "."
             chmod +x crypt.sh
             ./crypt.sh decrypt -p ${vault_password} --path ${CONTOUR}
       """
            sh "rm supply -rf; mkdir supply"
            dir("supply") {
                git branch: "${Version_supply}",
                        credentialsId: 'git_key',
                        url: "ssh://git@git.sfera.vtb.ru:7999/${globalSystem}_deploy/supply-ah-${globalSystem}.git"
            }
            withCredentials([string(credentialsId: "kuber_token", variable: 'TTOKEN')]) {
                DEPLOY_STATUS = sh(
                        script: """
                        #!/usr/bin/env bash
                        cp -R ./supply/* ./inventory/K4
                        ls
                        ${oc}/oc login --server=${k8s_cluster_api} --token="${TToken}" --insecure-skip-tls-verify
                        ${oc}/oc project "${k8s_namespace}"
                        ${helm}/helm upgrade -n "${k8s_namespace}" -i --set service.version=${Version} ${JOB_BASE_NAME} ${CONTOUR} --values ${CONTOUR}/values-prep.yaml --values secret-prep.yaml --dry-run > /dev/null
                        ${helm}/helm upgrade -n "${k8s_namespace}" -i --set service.version=${Version} ${JOB_BASE_NAME} ${CONTOUR} --values ${CONTOUR}/values-prep.yaml --values secret-prep.yaml --atomic
                        ${oc}/oc logout

                        """,
                        returnStatus: true
                )
            }

        }
            // Для корректного завершения stage, если произошла ошибка
            if (DEPLOY_STATUS == 0) {
                echo 'Команда выполнена успешно'
            } else {
                error 'Команда завершилась с ошибкой'
            }
            env.DEPLOY_STATUS = DEPLOY_STATUS
        }
    }
