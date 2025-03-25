package funcutil

def ms_build(Map yamlData, int __c__){
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
        lst_params.add(gitParameter(
            name: 'BRANCH_NAME',
            branch: 'main',
            description: 'Необходимо выбрать ветку для сборки',
            tagFilter: '*',
            type: 'BRANCH_TAG',
            defaultValue: 'main',
            quickFilterEnabled: true,
            branchFilter: 'origin/(.*)',
            sortMode: 'NONE',
            selectedValue: 'NONE',
            useRepository: "ssh://git@github.com/${yamlData.pipelines[__c__].parameters.globalSystem}/${yamlData.pipelines[__c__].parameters.GitName}.git"
        )
    )
        lst_params.add(booleanParam('Move_Distr', false, 'Перемещение дистрибутива для перекладки в CDL'))
        lst_params.add(booleanParam('Deploy_to_dev', false, 'Установка пакета на DSO'))
        if (yamlData.pipelines[__c__].parameters.Platform == 'OC') {
            lst_params.add(stringParam('Version_inv', '', 'Указываем вертку Inv'))
            lst_params.add(stringParam('Version_supply', '', 'Указываем ветку Supply'))
        }
        lst_params.add(booleanParam('Debug', false, 'Включение Debug'))
    }
    lst_params.add(booleanParam('test', true, 'test'))
    return lst_params
}

def test(){
    echo 'worked'
}
