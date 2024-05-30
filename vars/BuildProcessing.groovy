pipelineJob(pipeline.parameters.name) {
    parameters {
        //GitParam(branchFilter: 'origin/(.*)', branch: 'master', description: '', gitTool: 'Default', name: 'BRANCH_NAME', quickFilterEnabled: true, selectedValue: 'NONE', sortMode: 'NONE', tagFilter: '*', type: 'PT_BRANCH', useRepository: '0')
        booleanParam('Move_Distr', false, 'Перемещение дистрибутива для перекладки в CDL')
        booleanParam('Deploy_to_dev', false, 'Установка пакета на DSO')
    }
    definition {
        cps {
            script("""@Library('lab') _
SferaRunPipeline(
name: '${pipeline.parameters.name ?: 'NULL'}',
mono: '${pipeline.parameters.mono ?: 'false'}',
mvncommand: '${pipeline.parameters.mvncommand ?: 'jar'}',
GitName: '${pipeline.parameters.GitName ?: 'test'}',
Platform: '${pipeline.parameters.Platform ?: 'OC'}',
globalSystem: '${pipeline.parameters.globalSystem ?: 'test2'}'${pipeline.parameters.projectver ? ",\nprojectver: '${pipeline.parameters.projectver}'" : ""}
)""")
            sandbox(true)
        }
    }
}