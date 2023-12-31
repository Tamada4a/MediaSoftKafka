# MediaSoftKafka
<ins>Цель</ins>:<br> 
познакомиться с основными принципами асинхронного взаимодействия микросервисов через брокер сообщения Kafka.<br> 
<ins>Задачи</ins>:
<ul>
    <li>запустить Kafka в docker, использую docker-compose (можно взять отсюда https://github.com/GUR-ok/arch-brokerage-intercessor/blob/master/docker-compose.yml)</li>
    <li>создать микросервис ProducerService на SpringBoot, настроить продюсера сообщений, использую KafkaTemplate (можно использовать для справки этот проект https://github.com/GUR-ok/arch-brokerage-intercessor или любой интернет-ресурс). Топик создать с 1 партицией. Сервис должен иметь REST-эндпоинт, принимающий строку сообщения и ключ, который потом отправляется в топик Kafka. Почитать зачем нужен ключ.</li>
    <li>создать микросервис ConsumerService на SpringBoot, настроить консьюмер сообщений, читающий сообщения из созданного топика, вычитанное сообщение сохранять в БД или просто печатать в консоль. Обратить внимание на groupId.</li>
    <li>запустить 1 экз. продьюсера, 2 экз. консьюмера. У консьюмеров одинаковый groupId. Посмотреть как обрабатываются сообщения. Какой из сервисов-коньюмеров обрабатывает сообщения.</li>
    <li>создать новый топик с двумя партициями. Также запустить сервисы 1экз+2экз. Отправить сообщения с разными ключами. Отправить несколько сообщений с одинаковым ключом. Посмотреть какой из экземпляров обрабатывает сообщения.</li>
    <li>у 2экз. консьюмеров сделать разный groupId. Отправить сообщения и проанализировать результат.</li>
</ul>
<ins>Результат</ins>:<br> 
По итогам задачи нужно уметь объяснить:
<ul>
    <li>как обрабатываются сообщения, если консьюмеров больше чем партиций в топике</li>
    <li>зачем передается ключ вместе с сообщением</li>
    <li>зачем нужен groupId</li>
    <li>гарантия доставки и порядок сообщений в партиции в Kafka</li>
    <li>семантика доставки (atLeastOnce, atMostOnce, ExactlyOnce)</li>
</ul>

## Ответы на вопросы
#### 1. Как обрабатываются сообщения, если консьюмеров больше чем партиций в топике?
Если консьюмеров больше, чем партиций, то выбираются какие-то рандомные консьюмеры (по числу партиций) и все поступающая нагрузка распределяются между ними

#### 2. Зачем передается ключ вместе с сообщением?
Ключи позволяют распределить нагрузку по консьюмерам внутри группы

#### 3. Зачем нужен groupId?
С целью параллельной обработки поступающих данных разными группами консьюмеров. Например: на выходе у нас есть подтверждённая оплата - сервис уведомлений получает данные того, кто оплатил и отправляет уведомление; в это же время служба доставки получает данные этого же человека, чтоб оформить доставку

#### 4. Гарантия доставки и порядок сообщений в партиции в Kafka
В Kafka реализован порядок ***"первый вошёл - первый вышел"***. Сообщения доставляются по принципам ***At-most-once delivery (как максимум однократная доставка)*** и ***At-least-once delivery (как минимум однократная доставка)***

#### 5. Семантика доставки (atLeastOnce, atMostOnce, ExactlyOnce)
<ul>
    <li><strong>At-most-once delivery (как максимум однократная доставка)</strong> - сообщение доставится максимум один раз, при этом оно может быть потеряно</li>
    <li><strong>At-least-once delivery (как минимум однократная доставка)</strong> - сообщение точно будет доставлено один раз, при этом оно никогда не будет потеряно</li>
    <li><strong>Exactly-once delivery (строго однократная доставка)</strong> - сообщение доставляется строго один раз</li>
</ul>