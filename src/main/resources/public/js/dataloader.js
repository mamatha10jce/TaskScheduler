$(function () {

  var taskTable = $('#taskTable');
  initialTaskLoad(taskTable);

  var loadAsyncButton = $('#loadTaskAsync');
  loadAsyncButton.click(function(e) {
    initializeEventSource(taskTable);
    loadAsyncButton.attr('disabled', true);
  });


});

function initialTaskLoad(taskTable) {
  $.ajax({
    url: 'http://localhost:8888/api/scheduler/load/tasks',
    method: 'GET',
    success: function (dataArray) {
      updateTaskTableOnDataLoad(taskTable, dataArray);
    },
    error: function () {
      alert('Unable to load tasks currently')
    }
  });
}

function updateTaskTableOnDataLoad(table, dataArray) {
  if (Array.isArray(dataArray)) {
    dataArray.forEach(function (item) {
      var allTableCellString = "";
      Object.keys(item).forEach(function (key) {
        allTableCellString += getTableCellString(key, item[key]);
      });
      var trString = "<tr style='border-bottom: 1px solid black'>"
          + allTableCellString + "</tr>";
      table.append(trString);
    });
  }
}

function getTableCellString(key, value) {
  if (['dueDate', 'priority'].indexOf(key) > -1) {
    return "<td style='border-right: 1px solid black; border-collapse: collapse; background-color: lightgray'>"
        + value + "</td>";
  }
  return "<td style='border-right: 1px solid black; border-collapse: collapse'>"
      + value + "</td>";
}

function initializeEventSource(table) {
  const eventSource = new EventSource('http://localhost:8888/api/scheduler/load/async/tasks');
  eventSource.onmessage = function(e) {
    updateTaskTableOnDataLoad(table, [JSON.parse(e.data)]);
  };

  eventSource.onerror = function (e) {
    if (e.readyState == EventSource.CLOSED) {
      alert('The server closed the stream');
    }
    else {
      console.log(e);
    }
  };
  eventSource.addEventListener('second', function(e) {
    console.log('second', e.data);
  }, false);
}

