def rekurwa(String stroka, int i=0 ,List<String> res=[]){
    sp_str = stroka.split('/')
    if(i != sp_str.size()-1){
        res.add(sp_str[0..i].join('/'))
        return rekurwa(stroka, i+1, res)
    }else{
        return res
    }
}

def git_file_cleaner(git_url, days){
    stage('cleane_git'){
        // ssh://git@github.com/Zelgladis/JenkisTestGit.git
        sshagent (credentials: ["git_token"]) {
            script {
        sh"""
            #!/bin/bash
            # Параметры

            REPO_URL="$git_url"         # URL репозитория
            REPO_PATH="./new_repo"      # Локальный путь, куда будет клонирован репозиторий
            DAYS_OLD=$days              # Количество дней для фильтрации файлов

            # Клонирование репозитория
            if [ -d "\$REPO_PATH" ]; then
            rm -rf "\$REPO_PATH"  # Очистка предыдущей версии
            fi
            git clone "\$REPO_URL" "\$REPO_PATH"
            cd "\$REPO_PATH" || exit 1

            # Проверка, что клонирование прошло успешно
            if [ ! -d ".git" ]; then
            echo "Ошибка: Репозиторий не был клонирован!"
            exit 1
            fi

            # Находим файлы, которые не менялись более \$DAYS_OLD дней
            echo "Находим файлы, которые не менялись более \$DAYS_OLD дней"
            # OLD_FILES=\$(git ls-files -z | xargs -0 -I{} sh -c 'if [ "\$(git log -1 --format="%ct" -- {} | awk -v days="\$DAYS_OLD" "{ if ((systime() - (\$1)) > (days * 24 * 3600)) print 1; }")" == "1" ]; then echo {}; fi')
            
            OLD_FILES=\$(git ls-files -z | xargs -0 -I{} bash -c '
            LAST_MODIFIED=\$(git log -1 --format="%ct" -- "\$1")
            if (( \$(date +%s) - LAST_MODIFIED > \$2 * 24 * 3600 )); then
                echo "\$1"
            fi
            ' -- {} "\$DAYS_OLD")
            # Удаление старых файлов
            for FILE in \$OLD_FILES; do
            rm -f "\$FILE"
            echo "Удалён файл: \$FILE"
            done

            # Проверка наличия изменений и коммит
            if [ -n "\$(git status --porcelain)" ]; then
            git config user.name "Jenkins CI"
            git config user.email "jenkins@example.com"
            
            git add .
            git commit -m "Удалены файлы старше \$DAYS_OLD дней"
            
            # Пушим изменения обратно в репозиторий
            git push origin main  # замените main на нужную ветку, если требуется
            else
            echo "Нет файлов для удаления."
            fi

        """
            }
        }
    }
}

def git_branch_cleaner(Map args){
    stage('cleane_branch_git'){
        sshagent (credentials: ["git_token"]) {
            script {
                def REPO_URL = args.REPO_URL
                def DAYS_OLD = args.DAYS_OLD
                sh"""#!/bin/bash
                    # Удаление старых веток
                    # Параметры
                    REPO_URL="$REPO_URL"          # URL вашего репозитория
                    REPO_PATH="./mrepos"            # Локальный путь, куда будет клонирован репозиторий
                    DAYS_OLD="$DAYS_OLD"        # Количество дней для фильтрации веток

                    # Клонируем репозиторий (только для чтения веток, не клонируем всю историю)
                    if [ -d "\$REPO_PATH" ]; then
                        rm -rf "\$REPO_PATH"  # Очистка предыдущей версии
                    fi
                    git clone "\$REPO_URL" "\$REPO_PATH"
                    ls
                        cd "\$REPO_PATH" || exit 1
                    ls
                    # Проверка, что клонирование прошло успешно
                    if [ ! -d ".git" ]; then
                        echo "Ошибка: Репозиторий не был клонирован!"
                    exit 1
                    fi

                    # Получаем список удаленных веток
                    OLD_BRANCHES=()
                    for branch in \$(git for-each-ref --format '%(refname:short) %(committerdate:unix)' refs/remotes | awk -v days="\$DAYS_OLD" '{if ((systime() - \$2) > (days * 24 * 3600)) print \$1}'); do
                        # Исключаем основную ветку (обычно main или master)
                        if [[ "\$branch" != "origin/main" && "\$branch" != "origin/master" ]]; then
                            OLD_BRANCHES+=("\$branch")
                        fi
                    done

                    # Удаление старых веток
                    if [ \${#OLD_BRANCHES[@]} -eq 0 ]; then
                        echo "Нет старых веток для удаления."
                    else
                        for branch in "\${OLD_BRANCHES[@]}"; do
                            # Удаление ветки на удаленном сервере
                            #git push origin --delete "\${branch#origin/}"
                            echo "Удалена старая ветка: \${branch#origin/}"
                    done
                    fi
                """
            }
        }
    }
}

