class MyClass {
  String var = 'TestString'
  String name = 'Sergey'
  String yami
  String d2

  MyClass(String d2){
    this.yami = 'Darkness'
    this.d2 = d2
  }

  MyClass() {
        // По умолчанию установим сообщение
        this.yami = 'Darkness'
        this.d2 = 'default'
    }

  void printIt() {
        echo "JDK_HOME is: ${this.yami}"
    }
}
