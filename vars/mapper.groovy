import groovy.transform.Field

def call(){
    myArgs.put('hy', 'hello')
}

def git_branch_cleaner2(Map args){
    stage('cleane_branch_git'){
        sshagent (credentials: ["git_token"]) {
            script {
                def REPO_URL = args.REPO_URL
                def DAYS_OLD = args.DAYS_OLD
                sh """#!/bin/bash
                    # Удаление старых веток
                    REPO_URL="$REPO_URL"          # URL вашего репозитория
                    REPO_PATH="./mrepos"          # Локальный путь, куда будет клонирован репозиторий
                    DAYS_OLD="$DAYS_OLD"          # Количество дней для фильтрации веток

                    # Пороговое время для сравнения
                    THRESHOLD_TIME=\$(( \$(date +%s) - DAYS_OLD * 24 * 3600 ))

                    # Клонируем репозиторий (только для чтения веток)
                    if [ -d "\$REPO_PATH" ]; then
                        rm -rf "\$REPO_PATH"
                    fi
                    git clone --no-checkout "\$REPO_URL" "\$REPO_PATH"
                    cd "\$REPO_PATH" || exit 1

                    # Проверка успешного клонирования
                    if [ ! -d ".git" ]; then
                        echo "Ошибка: Репозиторий не был клонирован!"
                        exit 1
                    fi

                    # Получаем список старых веток для удаления
                    OLD_BRANCHES=()
                    echo "Текущий порог времени: \$THRESHOLD_TIME"
                    
                    mapfile -t OLD_BRANCHES < <(git for-each-ref --format '%(refname:short) %(committerdate:unix)' refs/remotes | \
                        awk -v threshold="\$THRESHOLD_TIME" '{if (\$2 < threshold) print \$1}')
                    
                    # Исключаем основную ветку (обычно main или master)
                    #OLD_BRANCHES=( "\${OLD_BRANCHES[@]/origin\/main}" )
                    #OLD_BRANCHES=( "\${OLD_BRANCHES[@]/origin\/master}" )

                    # Удаление старых веток
                    if [ \${#OLD_BRANCHES[@]} -eq 0 ]; then
                        echo "Нет старых веток для удаления."
                    else
                        echo "Следующие ветки будут удалены:"
                        for branch in "\${OLD_BRANCHES[@]}"; do
                            echo "Удалена старая ветка: \${branch#origin/}"
                            # git push origin --delete "\${branch#origin/}"
                        done
                    fi
                """


            }
        }
    }
}