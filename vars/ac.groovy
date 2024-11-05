def call(){
    def choices = timeline() // Получаем актуальные значения
    stage("Выбор значения") {
        def selectedOption = input message: 'Выберите опцию', parameters: [
            choice(name: 'DynamicChoice', choices: choices, description: 'Select a dynamic option')
        ]
        echo "Selected option: ${selectedOption}"
    }
}