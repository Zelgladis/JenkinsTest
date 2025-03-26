def call(){
    stage('dsl_runer'){
        def yamlData = readYaml text: libraryResource('Service.yaml')
        def final_content = 'def yamlData = yamlData\n' + \
                            'def spaces = "\\n   "\n' + \
                            libraryResource('BuildUtilProc.groovy') + "\n"
        
        def folders = []
        yamlData.pipelines.each { pipeline ->
            if (pipeline.name.contains('/')) {
                def bober = myUtils.rekurwa(pipeline.name)
                def k8s_metka = pipeline.name.split('/')[1].toUpperCase()
                pipeline.parameters.sys_num = pipeline.name.split('/')[0]
                bober.each{
                    if(!folders.contains(it)){
                        folders.add(it)
                    }
                }
                if (k8s_metka == 'PS2') {
                    pipeline.parameters['k8s_cluster_api'] = 'SOME_PS2_API'
                    pipeline.parameters['k8s_namespace'] = 'PS2_NAMESPACE'
                    pipeline.parameters['values'] = 'values.ps2'
                }else if(k8s_metka == 'PS5'){
                    pipeline.parameters['k8s_cluster_api'] = 'SOME_PS5_API'
                    pipeline.parameters['k8s_namespace'] = 'PS5_NAMESPACE'
                    pipeline.parameters['values'] = 'values.ps5'
                }
            }
            if(!pipeline.parameters.containsKey('Platform')){
                pipeline.parameters['Platform'] = 'OC'
            }
            if(!pipeline.parameters.containsKey('globalSystem')){
                pipeline.parameters['globalSystem'] = 'cnts'
            }
        }
        
        folders.each{
            final_content = final_content + "folder('${it}'){}\n"
        }
        //final_content = final_content + "folder('${builded_path}'){}\n"
        def jobContent = libraryResource('BuildProc_2.groovy')
        for(int i=0; i < yamlData.pipelines.size(); i++){
            final_content = final_content + "\n" +(jobContent.replace("__c__", "${i}"))
        }
        println final_content
        writeFile(file: "BuildProc_2.groovy", text: "${final_content}")
        jobDsl targets: "BuildProc_2.groovy",
                lookupStrategy: 'SEED_JOB',
                ignoreExisting: false,
                removedJobAction: "DELETE",
                additionalParameters: [
                        yamlData: yamlData,
                ],
                sandbox: true
    }
}
