// vars/test.groovy

def call() {
  echo 'Test Lib'
  n_mclass = new MyClass('Hello world')
  echo "${n_mclass.yami}"
}
