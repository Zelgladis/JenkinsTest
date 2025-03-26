pipelineJob('deployAll') {
    definition {
        cps {
            def var_block = ""
            // Получаем красивый паплайн
            def scriptContent = "@Library('lab') _\n" + \
                "RunDeployAll()"
            script(scriptContent)
            sandbox(true)
        }
    }
}
