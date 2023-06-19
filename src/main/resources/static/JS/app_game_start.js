window.onload = function() {
    questionDisplay();
    selectDisplay();
    startStopwatch();
}

var count = 0;

document.getElementById('next').addEventListener('click', () => {
    count++;
    document.getElementById('i').innerText = count;
    questionDisplay();
    selectDisplay();
})

document.getElementById('back').addEventListener('click', () => {
    count--;
    document.getElementById('i').innerText = count;
    questionDisplay();
    selectDisplay();
})

function questionDisplay() {
    const id = document.getElementById('userGameId').innerText;
    const i = document.getElementById('i').innerText;
    fetch(`/game_start?userGameId=${id}&i=${i}`)
    .then(res => {
        if(res.status === 400) {
          window.alert('Hello JavaScript');
        } else {
          res.json()
          .then(data => {
            document.getElementById('categoryName').textContent = data.categoryName;
            document.getElementById('questionName').textContent = data.questionName;
            document.getElementById('questionText').textContent = data.questionText;
            document.getElementById('questionId').textContent = data.questionId;
          })
        }
    });
    if(i == 4) {
        document.getElementById("ok").style.display = "flex";
    } else {
        document.getElementById('ok').style.display = "none";
    }
    if(i == 0) {
        document.getElementById("back").style.display = "none";
    } else {
        document.getElementById("back").style.display = 'flex';
    }
}

function selectDisplay() {
    const id = document.getElementById('userGameId').innerText;
    const i = document.getElementById('i').innerText;
    fetch(`/game_start/select?userGameId=${id}&i=${i}`)
    .then(res => {
      if(res.status === 400) {
        window.alert('Hello error');
      } else {
        res.json()
        .then(data => {

        var radioButtons = document.getElementsByName("select");
        var countI = 0;
        var memes = document.getElementsByClassName("meme");
        data.forEach((selectTest) => {
              radioButtons[countI].value = selectTest.answer;
              radioButtons[countI].textContent = selectTest.choiceText;
              memes[countI].textContent = selectTest.choiceText;
              countI++;
        })

        })
      }
    });
}



function getSelectedValue() {
  // ラジオボタン要素の取得
  var radios = document.getElementsByName('select');

  // 選択されているラジオボタンのvalue値を取得
  for (var i = 0; i < radios.length; i++) {
    if (radios[i].checked) {
      var selectedValue = radios[i].value;
      console.log(selectedValue); // 取得したvalue値をコンソールに表示
      break;
    }
  }
}


// ストップウォッチの処理
var stopwatchInterval;
var stopwatchTime = 0;
var mm = document.getElementById("m");
var ss = document.getElementById("s");

function startStopwatch() {
  var stopwatchElement = document.getElementById("stopwatch");

  stopwatchInterval = setInterval(function() {
    stopwatchTime++;
    var minutes = Math.floor(stopwatchTime / 60);
    var seconds = stopwatchTime % 60;

    mm.innerText = minutes;
    ss.innerText = seconds;

    stopwatchElement.innerText = minutes + ":" + formatSeconds(seconds);
  }, 1000);
}

function stopStopwatch() {
  clearInterval(stopwatchInterval);
  var stopwatchElement = document.getElementById("stopwatch");
  var clearTimeSet = document.getElementById("clearTime");
  clearTimeSet.innerText = stopwatchElement.innerText;
  document.getElementById("clearOverlayId").style.display = "flex";
  document.getElementById("clearPopupId").style.display = "flex";

  var m = mm.innerText;
  var s = ss.innerText;
  var stopWatch = stopwatchElement.innerText;

  if (level.innerText === 'normal') {
  fetch(`/playTest-score?m=${m}&s=${s}&stopWatch=${stopWatch}`)
  } else {
  fetch(`/playTest-scoreH?m=${m}&s=${s}&stopWatch=${stopWatch}`)
  }
}

// 秒を2桁表示にフォーマットする
function formatSeconds(seconds) {
  return seconds < 10 ? "0" + seconds : seconds;
}