pipelineJob(yamlData.pipelines[**c**].name) {
    parameters {
        booleanParam('Move_to_IFT', false, 'Перемещение дистрибутива')
        booleanParam('Deploy_to_IFT', false, 'Установка пакета')
        if(yamlData.pipelines[**c**].parameters.mono == 'XYZ' ){
            booleanParam('Move_321', false, 'XYZ')
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
