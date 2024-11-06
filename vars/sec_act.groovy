def call(){
    my_params.add(paramen_ret(type: 'choice',name: 'VersionRollback', choices: timeline() ,description: 'Select a version to rollback'))
    param_init.reInit()
}