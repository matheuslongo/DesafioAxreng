## Execução
A partir do diretório raiz do projeto, os seguintes comandos, executados em sequência, devem fazer a
compilação e execução da aplicação:


docker build . -t axreng/backend

docker run -e BASE_URL=http://hiring.axreng.com/ -e KEYWORD=four --rm axreng/backend


## Variáveis de ambiente
-** BASE_URL:** Definição obrigatória. Determina a URL base do website em que a busca deve ser feita pela aplicação. Restrições: o valor deve conter uma URL (HTTP ou HTTPS) válida e absoluta de acordo com a implementação da classe java.net.URI.

-** KEYWORD:** Definição obrigatória. Determina o termo (sequência de caracteres) a ser buscado no conteúdo das páginas visitadas pela aplicação. Restrições: o valor deve conter no mínimo 4 e no máximo 32 caracteres, e deve conter apenas caracteres alfanuméricos.

-** MAX_RESULTS:** Definição opcional. Determina o número máximo de resultados que devem ser retornados por uma busca. Restrições: O valor deve representar um número inteiro igual a -1 (indicando limite não definido, ou seja, busca ilimitada) ou maior do que 0. Quando o valor não é especificado, ou é especificado um valor inválido, a aplicação deve assumir o valor default -1. Quando uma busca atinge o limite de resultados, ela deve ser concluída, deixando de visitar novos links encontrados. Buscas com limite máximo definido podem retornar resultados diferentes de acordo com a estratégia adotada para navegação entre links; diferenças desse tipo são aceitáveis e não prejudicarão sua avaliação.