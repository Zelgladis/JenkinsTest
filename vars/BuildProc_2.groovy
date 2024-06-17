//p_val.add(Jenkins.instance.getItemByFullName(yamlData.pipelines[c].name)?.getProperty(hudson.model.ParametersDefinitionProperty)?.getParameterDefinition('PARAM_NAME')?.defaultValue)
//p_val.add(job(yamlData.pipelines[c].name)?.getProperty(ParametersDefinitionProperty.class)?.getParameterDefinition('PARAM_NAME')?.defaultValue)
p_val = binding.variables.PARAM_NAME.defaultValue

pipelineJob(yamlData.pipelines[c].name) {
    parameters {
            gitParameter {
                name('BRANCH_NAME')
                branch('main')
                description('Необходимо выбрать ветку для сборки')
                tagFilter('*')
                type('BRANCH_TAG')
                defaultValue('main')
                quickFilterEnabled(true)
                branchFilter('origin/(.*)')
                sortMode('NONE')
                selectedValue('NONE')
                useRepository("ssh://git@github.com/${yamlData.pipelines[c].parameters.globalSystem}/${yamlData.pipelines[c].parameters.GitName}.git")
            }
              credentialsParam('DEPLOY_KEY') {
                type('org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl')
                required()
                defaultValue('QWE')
                description('Решение всех проблем')
            }
            //p_val = job.getProperty(ParametersDefinitionProperty)?.getParameterDefinition('PARAM_NAME').getDefaultParameterValue()?.value
            if (binding.variables.PARAM_NAME != null){
              p_val = binding.variables.PARAM_NAME
            }
            //binding.variables.each {
            //  println "${it.key} = ${it.value}"
            //}
            //println job(yamlData.pipelines[c].name).params.get('PARAM_NAME')
            if (p_val[c] == null) {
                stringParam('PARAM_NAME', 'default_value', 'Description of the parameter')
            } else {
                // Если параметр уже существует, сохраняем его текущее значение
                stringParam('PARAM_NAME', p_val, 'Description of the parameter')
            }


      if (yamlData.pipelines[c].parameters.mvncommand == 'dotnet nupkg') {
        stringParam('VersionNupkg', '2.0.', 'Версия пакетов')}
      else {
        booleanParam('Move_Distr', false, 'Перемещение дистрибутива для перекладки в CDL')
        booleanParam('Deploy_to_dev', false, 'Установка пакета на DSO')
        if (yamlData.pipelines[c].parameters.Platform == 'OC') {
          stringParam('Version_inv', '', 'Указываем вертку Inv')
          stringParam('Version_supply', '', 'Указываем ветку Supply')
        }
        booleanParam('Debug', false, 'Включение Debug')
      }
    }
    definition {
        cps {
            def var_block = "" + \
                "${(yamlData.pipelines[c].parameters.mono ? "${spaces}mono: '${yamlData.pipelines[c].parameters.mono}'," : "")}" + \
                "${(yamlData.pipelines[c].parameters.mvncommand ? "${spaces}mvncommand: '${yamlData.pipelines[c].parameters.mvncommand}'," : "")}" + \
                "${(yamlData.pipelines[c].parameters.GitName ? "${spaces}GitName: '${yamlData.pipelines[c].parameters.GitName}'," : "")}" + \
                "${(yamlData.pipelines[c].parameters.Platform ? "${spaces}Platform: '${yamlData.pipelines[c].parameters.Platform}'," : "")}" + \
                "${(yamlData.pipelines[c].parameters.globalSystem ? "${spaces}globalSystem: '${yamlData.pipelines[c].parameters.globalSystem}'," : "")}" + \
                "${(yamlData.pipelines[c].parameters.ProjectPath ? "${spaces}ProjectPath: '${yamlData.pipelines[c].parameters.ProjectPath}'," : "")}" + \
                "${(yamlData.pipelines[c].parameters.k8s_namespace ? "${spaces}k8s_namespace: '${yamlData.pipelines[c].parameters.k8s_namespace}'," : "")}" + \
                "${(yamlData.pipelines[c].parameters.k8s_cluster_api ? "${spaces}k8s_cluster_api: '${yamlData.pipelines[c].parameters.k8s_cluster_api}'," : "")}" + \
                "${(yamlData.pipelines[c].parameters.server_name ? "${spaces}server_name: '${yamlData.pipelines[c].parameters.server_name}'," : "")}" + \
                "${(yamlData.pipelines[c].parameters.standServiceDir ? "${spaces}standServiceDir: '${yamlData.pipelines[c].parameters.standServiceDir}'," : "")}" + \
                "${(yamlData.pipelines[c].parameters.DotNetDockerPath ? "${spaces}DotNetDockerPath: '${yamlData.pipelines[c].parameters.DotNetDockerPath}'," : "")}"  + \
                "${(yamlData.pipelines[c].parameters.mvntest ? "${spaces}mvntest: '${yamlData.pipelines[c].parameters.mvntest}'," : "")}" + \
                "${(yamlData.pipelines[c].parameters.service_path ? "${spaces}service_path: '${yamlData.pipelines[c].parameters.service_path}'," : "")}" + \
                "${(yamlData.pipelines[c].parameters.pom_path ? "${spaces}pom_path: \"${yamlData.pipelines[c].parameters.pom_path}\"," : "")}"
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
