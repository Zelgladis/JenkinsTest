// vars/mpline.groovy

def call() {
    echo 'Worked!'
}

def init_env() {
    node () {
        stage ("Init ENV"){
            // do stuff here
            def envs = new N_Class('H')
            //HERE CAN ADD CUSTOM VAR TO CLASS
            envs.add_env('asd','000000')
            envs.add_env('cringe', envs.envs.yami)

            envs.get_envs().each{entry -> 
                env."${entry.key}" = "$entry.value"
            }

            //Тупо env.variable тоже работает и проще
            env.cringe2 = env.cringe
        }
    }
}


def step1(String x) {
    node () {
        if (x == 'test') {
            stage ("Good stage 1"){
            echo 'This is the first stage'
            echo env.asd
            echo "env.cringe2 = ${env.cringe2}"
        }
        } else {
            echo 'Skipping...'
        }
    }
}

def step_print() {
    node () {
        stage ("step_print"){
            // do stuff here
            echo 'This is the first stage'
            echo "${env.cringe}"
            sh """
            echo "$cringe"
            """
        }
    }
}

def failed_step(x){
    node(){
        stage('failed_step'){
            if(x == 'true'){
                echo 'not failed'
            }
        }
    }
}

def clean(){
    node () {
        stage ("Cleaning..."){
            // do stuff here
            echo 'Clean complete!'
        }
    }
}

def myFirstStage() {
    stage('First Stage') {
        echo 'This is the first stage'
    }
}

def mySecondStage(String envVar) {
    stage('Second Stage') {
        echo "This is the second stage $envVar"
    }
}


def myNewFirstStage() {
    stage('New First Stage') {
        echo 'This is my new first stage'
    }
}