def call(args){
    node () {
        stage ("Init ENV"){
            def envs = new N_Class('H')
            envs.add_env('asd','000000')

            envs.get_envs().each{entry -> 
                env."${entry.key}" = "${entry.value}"
            }

            args.each{arg -> 
                env."${arg.key}" = "${arg.value}"
            }
            env.cringe2 = env.cringe
            env.GIT_CREDENTIALS_ID = 'SSH_MIO' // ID ваших учетных данных в Jenkins
            env.MY_GIT = 'MY_GIT'
        }
    }
}