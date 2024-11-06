def call(List my_params){
    my_params.add(param_init.paramen_ret(type: 'choice',name: 'VersionRollback', choices: timeline() ,description: 'Select a version to rollback'))
    param_init.reInit()
}