def call(){
    node(){
        stage('cleane_git'){
            sshagent (credentials: ["git_token"]) {
                script {
            sh"""
                #!/bin/bash
                # Параметры
                REPO_URL="ssh://git@github.com/Zelgladis/JenkisTestGit.git"  # URL репозитория
                REPO_PATH="./chl"   # Локальный путь, куда будет клонирован репозиторий
                DAYS_OLD=180                                        # Количество дней для фильтрации файлов

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
                echo "\$DAYS_OLD"
                # OLD_FILES=\$(git ls-files -z | xargs -0 -I{} sh -c 'if [ "\$(git log -1 --format="%ct" -- {} | awk -v days="\$DAYS_OLD" "{ if ((systime() - (\$1)) > (days * 24 * 3600)) print 1; }")" == "1" ]; then echo {}; fi')
                
                OLD_FILES=\$(git ls-files -z | xargs -0 -I{} bash -c '
                LAST_MODIFIED=\$(git log -1 --format="%ct" -- "{}")
                if (( \$(date +%s) - LAST_MODIFIED > DAYS_OLD * 24 * 3600 )); then
                    echo "{}"
                fi
                ' -- DAYS_OLD="180")
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
                #git push origin main  # замените main на нужную ветку, если требуется
                else
                echo "Нет файлов для удаления."
                fi

            """
                }
            }
        }
    }
}