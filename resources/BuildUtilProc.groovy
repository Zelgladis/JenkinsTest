pipelineJob('deployAll') {
    definition {
        cps {
            def var_block = ""
            // Получаем красивый паплайн
            def scriptContent = "@Library('lab') _\n" + \
                "runDeployAll()"
            script(scriptContent)
            sandbox(true)
        }
    }
}
