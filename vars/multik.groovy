def call(){
    mpline.git_pull('main', 'https://github.com/Zelgladis/JenkinsTest.git')
    if (params.MY_PARAM?.trim()) {
        setParams([])
    }else{
        def my_params = []
        my_params.add(paramen_ret(type: 'bool',name: 'BoolTest', valueBool: true, description: 'Hello world'))
        my_params.add(paramen_ret(type: 'bool',name: 'BoolTest2', description: '2 world'))
        setParams(my_params)
    }
}

def setParams(List my_params){
    my_params = my_params.findAll { it != null }
    properties([
            parameters(my_params)
        ])
}