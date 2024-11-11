import groovy.transform.Field

def call(){
    env.myArgs.put('hy', 'hello')
}