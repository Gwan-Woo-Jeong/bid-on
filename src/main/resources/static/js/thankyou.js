console.log(item.orderCreateDate);
const orderDate = dayjs(item.orderCreateDate);
console.log(orderDate);
const estimatedDeliveryDate = orderDate.add(item.deliveryDayCount, 'day');
$('#deliveryDate').text(estimatedDeliveryDate.format('YYYY-MM-DD'));
