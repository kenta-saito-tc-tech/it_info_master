window.onload = function() {
    questionDisplay();
    selectDisplay();
    startStopwatch();
}

var count = 0;

document.getElementById('next').addEventListener('click', () => {
    getSelectedValue();
    count++;
    if(count == 5) {
        document.getElementById("ok").style.display = "flex";
        stopStopwatch();
        return;
    } else {
        document.getElementById('ok').style.display = "none";
    }
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
    var questionId = document.getElementById('questionId').innerText;
    var dateId = document.getElementById('userGameId').innerText;
    var selectedValue;
    var answerNum;

    // 選択されているラジオボタンのvalue値を取得
    for (var i = 0; i < radios.length; i++) {
        if (radios[i].checked) {
          selectedValue = radios[i].value;
          console.log(selectedValue); // 取得したvalue値をコンソールに表示
          break;
        }
    }

    if (selectedValue == 'true') {
        answerNum = 1;
        console.log(answerNum);
    } else {
        answerNum = 0;
        console.log(answerNum);
    }

    // 送信するデータを定義
    const data = {
        questionId: questionId,
        userAnswer: answerNum,
        dateId: dateId,
    };

    // リクエストを送信
    fetch('/game_start/answerInsert', {
        method: 'POST', // リクエストメソッドを指定
        headers: {
            'Content-Type': 'application/json', // リクエストのヘッダーにJSON形式でデータを送信することを指定
        },
        body: JSON.stringify(data), // リクエストのボディにデータをJSON形式で変換して送信
    })

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
  const id = document.getElementById('userGameId').innerText;

  var m = mm.innerText;
  var s = ss.innerText;

  const data = {
          id: id,
          minutes: m,
          seconds: s,
      };

      // リクエストを送信
      fetch('/game_start/timeUpdate', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json', // リクエストのヘッダーにJSON形式でデータを送信することを指定
          },
          body: JSON.stringify(data), // リクエストのボディにデータをJSON形式で変換して送信
      })
}

// 秒を2桁表示にフォーマットする
function formatSeconds(seconds) {
  return seconds < 10 ? "0" + seconds : seconds;
}