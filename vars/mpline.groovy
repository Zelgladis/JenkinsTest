// vars/mpline.groovy
import java.text.SimpleDateFormat

def call(args) {
    node(){
        init_env(args)
        git_pull(params.BRANCH_NAME, params.URL)
        dsl_runer_true()
    }
}

def create_dsl(){
    node(){
        git_pull('main', 'https://github.com/Zelgladis/JenkinsTest.git')
        dsl_run()
    }
}

def git_pull(bracnh_name, url){ 
    //kljkljlkjlkjlkkjljlkjljkj
    stage('🥵Git pull project') {
        info = git (
                branch: bracnh_name,
                credentialsId: 'git_token',
                url: url
        )
        sh 'ls'
        
    }
}

def yaml_read() {
    stage('yaml read'){
        def mkey = ''
        def mvalue = ''
        echo '--------------------------------------------'
        
        //def fex = fileExists 'conf.yml'
        if(fileExists('conf.yml1')){
            def configVal = readYaml file: "conf.yml"
            configVal.each{entry1 -> 
                entry1.value.each{entry2 -> 
                    echo "${entry2.key.toString()} : ${entry2.value.toString()}"
                }
            }
        }

    }
}


def step1(String x) {
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

def step_print() {
    stage ("step_print"){
        // do stuff here
        echo 'This is the first stage'
        echo "${env.cringe}"
        sh """
        echo "$cringe"
        """
    }
}

def failed_step(x){
    stage('failed_step'){
        if(x == 'true'){
            echo 'not failed'
        }
    }
}

def clean(){
    stage ("Cleaning..."){
        // do stuff here
        echo 'Clean complete!'
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

def pompom() {
    stage('Check Java Version') {
        def pomFiles = sh(
                returnStdout: true,
                script: 'find . -name pom.xml'
        ).trim()

        echo "${pomFiles}"

        def javaVersion = ''
        for (String pomFile : pomFiles.split("\\n")) {
            def version = sh(
                    returnStdout: true,
                    //script: "grep '<java.version>[^<]*</java.version>|<maven.compiler.source>[^<]*</maven.compiler.source>' $pomFile | sed -n 's/.*>\\([^<]*\\)<\\/\\(java.version\\|maven.compiler.source\\)>/\\1/p' | head -n 1"
                    script: """"\$pomFile" | sed -n '/<java\\.version\\|maven\\.compiler\\.source>/ { s/.*<\\(java\\.version\\|maven\\.compiler\\.source\\)>\\(.*\\)<\\/\\1>.*/\\2/p;q }'"""
            ).trim()
            if (!version.isEmpty()) {
                javaVersion = version
                break
            }
        }

        if (javaVersion.isEmpty()) {
            error("Java version not found in any pom.xml file")
        } else {
            echo "Java version found: $javaVersion"
        }
    }
}

def wtfworked() {
    stage('Check JDK Version') {
        def pomFiles = sh(returnStdout: true, script: 'find . -name pom.xml').trim().split('\n')

        def javaVersion = ""
        def mavenCompilerVersion = ""
        for (String pomFile : pomFiles) {
            def sourceTagPattern = /<(java\.version|maven\.compiler\.source)>([^<]+)<\/(?:java\.version|maven\.compiler\.source)>/
            def pomContent = sh(returnStdout: true, script: "cat $pomFile")

            def matcher = (pomContent =~ sourceTagPattern)
            if (matcher.find()) {
                if (!javaVersion && matcher.group(1) == "java.version") {
                    javaVersion = matcher.group(2)
                }
                if (!mavenCompilerVersion && matcher.group(1) == "maven.compiler.source") {
                    mavenCompilerVersion = matcher.group(2)
                }
                if (javaVersion && mavenCompilerVersion) {
                    break
                }
            }
        }

        def selectedVersion = javaVersion ?: mavenCompilerVersion ?: ""
        echo "Selected JDK version: $javaVersion" 
    }
}

def degra2(){
    stage('Hello World'){
            currentBuild.displayName = "Zen-${env.BUILD_NUMBER}"
            echo currentBuild.displayName
    }
}

def git_now(){
    stage('JenkinsTest GIT clone'){
        info = git (
            branch: 'main',
            credentialsId: 'git_key',
            url: 'https://github.com/Zelgladis/JenkinsTest.git'
        )
    }
    
}

def generate_pipe(){
    stage('Генерация DSL') {
        //checkout scm
        script {
            env.service_name = 'testovii'
            def jenkinsBaseDirectory = "${WORKSPACE}/.jenkins"
            def jobDslDir = "${jenkinsBaseDirectory}/JobDSL/"
            sh "ls"
            templateJobContent = readFile("vars/BuildProcessing.groovy")
            jobContent = (templateJobContent
                    .replace("service_name", "testovii2"))
            writeFile(file: ".jenkins/test_pipline/BuildProcessing.groovy", text: "${jobContent}")
        }
    }
}


def create_pipe(){
    stage('Запуск DSL') {
        def yamlData = readYaml file: "vars/Service.yaml"
        def conka = 'LOLIRUYU'
        jobDsl targets: '.jenkins/test_pipline/BuildProcessing.groovy',
                lookupStrategy: 'SEED_JOB',
                ignoreExisting: false,
                removedJobAction: "DELETE",
                additionalParameters: [
                        conka: conka,
                        yamlData: yamlData,
                ],
                sandbox: true
    }
}

def dsl_runer(){
    stage('dsl_runer'){
        def yamlData = readYaml file: "vars/Service.yaml"

        yamlData.pipelines.each { pipeline ->
            jobContent = readFile("vars/BuildProcessing.groovy")
            writeFile(file: ".jenkins/test_pipline/BuildProcessing.groovy", text: "${jobContent}")
            
            jobDsl targets: '.jenkins/test_pipline/BuildProcessing.groovy',
                    lookupStrategy: 'SEED_JOB',
                    ignoreExisting: false,
                    removedJobAction: "DELETE",
                    additionalParameters: [
                            pipeline: pipeline,
                    ],
                    sandbox: true
        }
    }
}

def splitPath(String path) {
    def segments = path.split('/')
    return generateSegments(segments, 0, [])
}

def generateSegments(String[] segments, int index, List<String> result) {
    if (index == segments.length - 1) {
        return result
    }
    result << segments[0..index].join('/')
    return generateSegments(segments, index + 1, result)
}

def rekurwa2(String stroka, List<String> folders=[], String prew=''){
    st_list = stroka.split('/')
    if(st_list.size() != 1){
        next_vals = st_list[1..(st_list.size()-1)].join('/')
        if(prew){
            prew = prew + '/' + st_list[0]
        }else{
            prew = st_list[0]
        }
        folders.add(prew)
        return rekurwa2(next_vals, folders, prew)
    }else{
        return folders
    }
}


def par_test()
{
    pipeline {
        agent 'any'
        
        parameters {
            string(name: 'VERSION', defaultValue: '1.0', description: 'Version of the application')
            booleanParam(name: 'DEPLOY', defaultValue: true, description: 'Deploy the application after build')
        }
        
        stages {
            stage('Build') {
                steps {
                    echo "Building version ${params.VERSION}"
                }
            }
            stage('Deploy') {
                when {
                    expression { params.DEPLOY == true }
                }
                steps {
                    echo "Deploying version ${params.VERSION}"
                    // Добавьте здесь шаги для развертывания
                }
            }
        }
    }
}


def test_backend(){
    stage('gradle'){
        sh"""
            ls $JAVA_HOME
            ./gradlew 'clean build' -x test
        """
    }
}

def runmaptest(mape){
    maptest(mape)
}

def maptest(mape){
    stage('Hello'){
    env.mye = 'hello World'
    mape.each{ m ->
        env."${m.key}" = "$m.value"
        echo env."${m.key}"
    }
    echo env.lol
    sh'echo $lol'
    sh'echo $lol'
    sh"echo ${lol}"
    sh'echo \${lol}'
    sh"echo ${env.lol}"
    }
}

def nex_stage(){
    stage('next_stage'){
        sh"""
            echo 'New Time'
            echo $lol
            echo $ser
        """
    }
}

def find_test(){
    stage('sex'){
        sh"""
        find '.' -type f -name '*.nupkg' -maxdepth 20 | while IFS= read -r file; do
            repo_file=\${file#./}
            echo "Upload: \$repo_file"
        done
        """
    }
}

def forach(){
    stage('forach sh') {
        def configVal = readYaml file: "conf.yml"
        def spis = configVal.someproj.nupkg
        env.spis = spis.join(',')

        sh """
            IFS=','
            for item in \$spis; do
                echo \$item
                cd "\$WORKSPACE/\$item"
                echo 'Some_Action'
            done
        """
    }
}

def fiif_test(){
    stage('IF TEST') {
        sh"""
            if [ "$hell" = "true" ]; then
                echo "true"
            else
                echo "false"
            fi
        """
    }
}

def mymain(){
    node(){
        //mapper()
        myArgs.put('123', '321')
        echo "${myArgs}"
    }
}

def folders(prefix){
    git_pull('main', 'https://github.com/Zelgladis/JenkinsTestGit.git')
    // Путь к директории
    def directoryPath = "./JenkinsTestGit"
    // Указанный релиз (начало имени папки)
    def releasePrefix = "release.22"

    // Регулярное выражение для извлечения даты
    def dateRegex = /(\d{2}-\d{2}_\d{2}-\d{2}-\d{4})/

    // Формат даты
    def dateFormat = new SimpleDateFormat("HH-mm_dd-MM-yyyy")

    // Получение списка папок из директории
    def folderNames = new File(directoryPath).listFiles()
        .findAll { it.isDirectory() && it.name.startsWith(releasePrefix) } // Фильтруем папки по префиксу
        .collect { it.name } // Берем только имена папок

    // Список для хранения пар: папка и распарсенная дата
    def parsedFolders = []

    folderNames.each { folder ->
        def matcher = (folder =~ dateRegex)
        if (matcher.find()) {
            def dateString = matcher[0] // Извлеченная строка с датой
            try {
                def date = dateFormat.parse(dateString) // Парсинг даты
                parsedFolders << [folder: folder, date: date] // Добавляем папку и дату в список
            } catch (Exception e) {
                println "Ошибка парсинга даты в папке: ${folder}"
            }
        }
    }

    // Сортировка папок по дате
    parsedFolders.sort { it.date }

    // Выбираем папку с самой последней датой
    def latestFolder = parsedFolders[-1]?.folder

    // Результат
    println "Самая последняя папка для релиза '${releasePrefix}': ${latestFolder}"
}

def folders2(prefix){
    git_pull('main', 'https://github.com/Zelgladis/JenkinsTestGit.git')
        //def str = '23-22_23-11-2024'
    //def ttime = new SimpleDateFormat("HH-mm_dd-MM-yyyy").parse(str) // Преобразование даты для сортировки
    def directoryPath = "./JenkinsTestGit"
    def folderNames = new File(directoryPath).listFiles()
        .findAll { it.isDirectory() } 
        .collect { it.name }

    def filteredFolders = folderNames.findAll { it.startsWith(prefix) }
    if (filteredFolders.isEmpty()) {
        println "Нет папок, соответствующих префиксу '${prefix}' в директории '${directoryPath}'"
        return
    }

    def sortedFolders = filteredFolders.sort { folder ->
    // Извлечение даты из формата release.22-дата
        def datePart = folder.length() >= 10 ? folder[-16..-1] : folder
        echo "$datePart"
        try {
                new SimpleDateFormat("HH24-MI_dd-mm-yyyy").parse(datePart) // Преобразование даты для сортировки
            } catch (Exception e) {
                println "Ошибка при обработке даты в папке: ${folder}"
                return new Date(0) // Устанавливаем минимальную дату в случае ошибки
            }
    }
    def latestFolder = sortedFolders[-1]

    println "$latestFolder"
}

//"dd/MM/yyyy HH:mm:ss"