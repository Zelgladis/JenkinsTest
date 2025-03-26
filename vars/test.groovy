// vars/test.groovy
import come.VarsM

def call() {
  echo 'Test Lib'
  def n_mclass = new MyClass('Hello world')
  echo "${n_mclass.yami}"
  echo "${n_mclass.d2}"
  echo depl


  def depl = VarsM[$]
  
}

def test() {
  text(name: 'MY_MULTI_STRING', defaultValue: 'Value1\nValue2\nValue3', description: 'Enter multiple strings separated by new lines')
  def konf_lst = params.CONTUR.split(' ')
  def depl = ''
  node(){
    if(params.DEPLOY_MS){
      for (i in konf_lst) {
        depl = VarsM.dice[i]
        stage("deploy to $depl"){
            println "Iteration: $depl"
        }
      }
    }
  }
}