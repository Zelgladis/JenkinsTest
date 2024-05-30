pipelineJob(yamlData.pipelines[**c**].name) {
    parameters {
            gitParameter {
                name('BRANCH_NAME')
                branch('master')
                description('Необходимо выбрать ветку для сборки')
                tagFilter('*')
                type('BRANCH_TAG')
                defaultValue('master')
                quickFilterEnabled(true)
                branchFilter('origin/(.*)')
                sortMode('NONE')
                selectedValue('NONE')
                useRepository("ssh://git@git/${yamlData.pipelines[**c**].parameters.globalSystem}/${yamlData.pipelines[**c**].parameters.GitName}.git")
            }
      if (yamlData.pipelines[**c**].parameters.mvncommand == 'dotnet nupkg') {
        stringParam('VersionNupkg', '2.0.', 'Версия пакетов')}
      else {
        booleanParam('Move_Distr', false, 'Перемещение дистрибутива для перекладки в CDL')
        booleanParam('Deploy_to_dev', false, 'Установка пакета на DSO')
        if (yamlData.pipelines[**c**].parameters.Platform == 'OC') {
          stringParam('Version_inv', '', 'Указываем вертку Inv')
          stringParam('Version_supply', '', 'Указываем ветку Supply')
        }
        booleanParam('Debug', false, 'Включение Debug')
      }
    }
    definition {
        cps {
            def var_block = "" + \
                "${(yamlData.pipelines[**c**].parameters.mono ? "${tabiki}mono: '${yamlData.pipelines[**c**].parameters.mono}'," : "")}" + \
                "${(yamlData.pipelines[**c**].parameters.mvncommand ? "${tabiki}mvncommand: '${yamlData.pipelines[**c**].parameters.mvncommand}'," : "")}" + \
                "${(yamlData.pipelines[**c**].parameters.GitName ? "${tabiki}GitName: '${yamlData.pipelines[**c**].parameters.GitName}'," : "")}" + \
                "${(yamlData.pipelines[**c**].parameters.Platform ? "${tabiki}Platform: '${yamlData.pipelines[**c**].parameters.Platform}'," : "")}" + \
                "${(yamlData.pipelines[**c**].parameters.globalSystem ? "${tabiki}globalSystem: '${yamlData.pipelines[**c**].parameters.globalSystem}'," : "")}" + \
                "${(yamlData.pipelines[**c**].parameters.projectver ? "${tabiki}projectver: '${yamlData.pipelines[**c**].parameters.projectver}'," : "")}" + \
                "${(yamlData.pipelines[**c**].parameters.asd ? "${tabiki}asd: '${yamlData.pipelines[**c**].parameters.asd}'," : "")}" + \
                "${(yamlData.pipelines[**c**].parameters.zxc ? "${tabiki}zxc: '${yamlData.pipelines[**c**].parameters.zxc}'," : "")}" + \
                "${(yamlData.pipelines[**c**].parameters.asdx ? "${tabiki}asdx: '${yamlData.pipelines[**c**].parameters.asdx}'," : "")}" + \
                "${(yamlData.pipelines[**c**].parameters.zxc2 ? "${tabiki}zxc2: '${yamlData.pipelines[**c**].parameters.zxc2}'," : "")}" + \
                "${(yamlData.pipelines[**c**].parameters.qqq ? "${tabiki}qqq: '${yamlData.pipelines[**c**].parameters.qqq}'," : "")}" + \
                "${(yamlData.pipelines[**c**].parameters.zxc3 ? "${tabiki}zxc3: '${yamlData.pipelines[**c**].parameters.zxc3}'," : "")}"
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
