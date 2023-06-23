window.onload = function() {
    resultTime();
    resultList();
}

function resultTime() {
    const userGameId = document.getElementById('userGameId').innerText;

    fetch(`/game_finish/result?userGameId=${userGameId}`)
    .then(res => {
        if(res.status === 400) {
          window.alert('Hello JavaScript');
        } else {
          res.json()
          .then(data => {
            const resultTime = data.gameScore;
            var m = Math.floor(resultTime / 60);
            var s = resultTime % 60;
            document.getElementById('minutes').textContent = m;
            document.getElementById('seconds').textContent = s;
          })
        }
    });
}

function resultList() {
    const userGameId = document.getElementById('userGameId').innerText;
    var falseCount = 0;

    fetch(`/game_finish/list?userGameId=${userGameId}`)
    .then(res => {
        if(res.status === 400) {
          window.alert('Hello JavaScript');
        } else {
          res.json()
          .then(data => {
          const listElement = document.createElement('div');
                data.forEach((list) => {
                const divElement = document.createElement('div');
                const marginElement = document.createElement('span');
                const spanElement = document.createElement('span');
                const buttonElement = document.createElement('button');
                marginElement.textContent=list.questionName;
                if(list.userAnswer == 1) {
                    spanElement.textContent = "〇";
                } else {
                    spanElement.textContent ="✖";
                    falseCount++;
                }
                buttonElement.type = "submit";
                buttonElement.name = "info";
                buttonElement.value = list.questionId;
                buttonElement.textContent = "詳細";
                divElement.appendChild(buttonElement);
                divElement.appendChild(spanElement);
                divElement.appendChild(marginElement);
                listElement.appendChild(divElement);
                })
                const c = document.getElementById('list');
                c.appendChild(listElement);

                //誤答数と追加秒数を追加
                document.getElementById('falseCount').textContent = falseCount;
                const addTime = falseCount * 10;
                document.getElementById('addTime').textContent = addTime;
          })
        }
    })
  }