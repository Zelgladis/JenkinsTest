class N_Class{
    HashMap envs

    N_Class(String var){
        envs = new HashMap()
        envs.put('JAVA_HOME', '/home/mio/dist/jdk-11.0.2')
        envs.put('GIT_USERNAME', 'zelgladis')
        envs.put('PACKMAN', 'zelcnts-ozin')
        envs.put('testing', "${envs.PACKMAN}")
        def matches = (envs.testing =~ /-(.*?)(-|$)/)
        def x = matches ? matches[0][1] : null
        envs.put('projectenv', x)

        if(var == 'true'){
            envs.put('loc', 'ru')
        }else{
            envs.put('loc', 'eng')
        }
    }

    void add_env(String mkey, String mvalue){
        envs.put(mkey, mvalue)
    }

    HashMap get_envs(){
        return envs
    }
}