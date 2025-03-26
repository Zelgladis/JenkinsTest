pipelineJob(yamlData.pipelines[__c__].name) {
    def lst_params = []
    //nonStoredPasswordParam('vault_password', 'Пароль для дешифровки')
    if (yamlData.pipelines[__c__].dep_key == 'true') {
        //credentialsParam('DEPLOY_KEY') {
        //  type('org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl')
        //  required()
        //  defaultValue('QWE')
        //  description('Решение всех проблем')
        //}
    }
    if (yamlData.pipelines[__c__].parameters.mvncommand == 'dotnet nupkg') {
        lst_params.add(stringParam('VersionNupkg', '2.0.', 'Версия пакетов'))
    }else {
        lst_params.add(booleanParam('Move_Distr', false, 'Перемещение дистрибутива для перекладки в CDL'))
        lst_params.add(booleanParam('Deploy_to_dev', false, 'Установка пакета на DSO'))
        if (yamlData.pipelines[__c__].parameters.Platform == 'OC') {
            lst_params.add(stringParam('Version_inv', '', 'Указываем вертку Inv'))
            lst_params.add(stringParam('Version_supply', '', 'Указываем ветку Supply'))
        }
        lst_params.add(booleanParam('Debug', false, 'Включение Debug'))
    }
    lst_params.add(booleanParam('test', true, 'test'))

    parameters {lst_params as List}
    definition {
        cps {
            def var_block = "" + \
                "${(yamlData.pipelines[__c__].parameters.mono ? "${spaces}mono: '${yamlData.pipelines[__c__].parameters.mono}'," : "")}" + \
                "${(yamlData.pipelines[__c__].parameters.mvncommand ? "${spaces}mvncommand: '${yamlData.pipelines[__c__].parameters.mvncommand}'," : "")}" + \
                "${(yamlData.pipelines[__c__].parameters.GitName ? "${spaces}GitName: '${yamlData.pipelines[__c__].parameters.GitName}'," : "")}" + \
                "${(yamlData.pipelines[__c__].parameters.Platform ? "${spaces}Platform: '${yamlData.pipelines[__c__].parameters.Platform}'," : "")}" + \
                "${(yamlData.pipelines[__c__].parameters.globalSystem ? "${spaces}globalSystem: '${yamlData.pipelines[__c__].parameters.globalSystem}'," : "")}" + \
                "${(yamlData.pipelines[__c__].parameters.ProjectPath ? "${spaces}ProjectPath: '${yamlData.pipelines[__c__].parameters.ProjectPath}'," : "")}" + \
                "${(yamlData.pipelines[__c__].parameters.k8s_namespace ? "${spaces}k8s_namespace: '${yamlData.pipelines[__c__].parameters.k8s_namespace}'," : "")}" + \
                "${(yamlData.pipelines[__c__].parameters.k8s_cluster_api ? "${spaces}k8s_cluster_api: '${yamlData.pipelines[__c__].parameters.k8s_cluster_api}'," : "")}" + \
                "${(yamlData.pipelines[__c__].parameters.server_name ? "${spaces}server_name: '${yamlData.pipelines[__c__].parameters.server_name}'," : "")}" + \
                "${(yamlData.pipelines[__c__].parameters.standServiceDir ? "${spaces}standServiceDir: '${yamlData.pipelines[__c__].parameters.standServiceDir}'," : "")}" + \
                "${(yamlData.pipelines[__c__].parameters.DotNetDockerPath ? "${spaces}DotNetDockerPath: '${yamlData.pipelines[__c__].parameters.DotNetDockerPath}'," : "")}"  + \
                "${(yamlData.pipelines[__c__].parameters.mvntest ? "${spaces}mvntest: '${yamlData.pipelines[__c__].parameters.mvntest}'," : "")}" + \
                "${(yamlData.pipelines[__c__].parameters.service_path ? "${spaces}service_path: '${yamlData.pipelines[__c__].parameters.service_path}'," : "")}" + \
                "${(yamlData.pipelines[__c__].parameters.pom_path ? "${spaces}pom_path: \"${yamlData.pipelines[__c__].parameters.pom_path}\"," : "")}" + \
                "${(yamlData.pipelines[__c__].parameters.target_list ? "${spaces}target_list: [${yamlData.pipelines[__c__].parameters.target_list.collect {"\n\t\t'${it}'" }.join(', ')}\n\t]," : "")}" + \
                "${(yamlData.pipelines[__c__].parameters.sys_num ? "${spaces}sys_num: \"${yamlData.pipelines[__c__].parameters.sys_num}\"," : "")}"
            // Получаем красивый паплайн
            def scriptContent = "@Library('lab') _\n" + \
                "RunPipelineX(" + \
                var_block.substring(0, var_block.length() - 1) + \
                "\n)"
            script(scriptContent)
            sandbox(true)
        }
    }
}
