// vars/run.pipe.groovy

def call(){
    mpline.git_now()
    mpline.dsl_runer_true()
    mpline.degra2()
}

def run_backend(){
    mpline.init_env()
    mpline.git_pull('master', 'https://github.com/Zelgladis/test_backend-todo-list.git')
    mpline.degra2()
}

def degra(){
    mpline.degra2()
}

def call1(Map args){
    '''
    qwer: 'Hello World', 
    name: 'Sergey',
    exs: Test
    '''
    try{
        mpline.init_env()
        mpline.git_pull()
        mpline.yaml_read()
        mpline.wtfworked()
        echo args.name
        echo args.qwer
        if(args.exs){
            echo args.exs
        }else{
            echo 'exs ton found!'
        }
        env.test = '1234567890'
        println "${test}"
        println '\${test}'
        println "${env.test}"
        println '\${env.test}'
        def xxx = """
            ${test}
            ${env.test}
            \${test}
            \${env.test}
        """
        println xxx
    }catch(Exception e){
        echo e
        throw Exception('WRONG!')
        
    }finally{
        mpline.clean()
    }
}
