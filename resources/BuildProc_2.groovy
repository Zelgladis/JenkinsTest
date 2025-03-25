pipelineJob(yamlData.pipelines[__c__].name) {
    if(yamlData.pipelines[__c__].parameters.clean_only != true){
      parameters {
        if (yamlData.pipelines[__c__].dep_key == 'true') {
        //credentialsParam('DEPLOY_KEY') {
        //  type('org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl')
        //  required()
        //  defaultValue('QWE')
        //  description('Решение всех проблем')
        //}
        }
        if (yamlData.pipelines[__c__].parameters.mvncommand == 'dotnet nupkg') {
            stringParam('VersionNupkg', '2.0.', 'Версия пакетов')
        }else {
            gitParameter([
                name: 'BRANCH_NAME',
                branch: 'main',
                description: 'Необходимо выбрать ветку для сборки',
                tagFilter: '*',
                type: 'BRANCH_TAG',
                defaultValue: 'main',
                quickFilterEnabled: true,
                branchFilter: 'origin/(.*)',
                sortMode: 'NONE',
                selectedValue: 'DEFAULT',
                useRepository: "ssh://git@github.com/${yamlData.pipelines[__c__].parameters.globalSystem}/${yamlData.pipelines[__c__].parameters.GitName}.git"
            ])
            booleanParam('Move_Distr', false, 'Перемещение дистрибутива для перекладки в CDL')
            booleanParam('Deploy_to_dev', false, 'Установка пакета на DSO')
            if (yamlData.pipelines[__c__].parameters.Platform == 'OC') {
                stringParam('Version_inv', '', 'Указываем вертку Inv')
                stringParam('Version_supply', '', 'Указываем ветку Supply')
            }
            booleanParam('Debug', false, 'Включение Debug')
        }
        booleanParam('test', true, 'test')
      }
    }
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
