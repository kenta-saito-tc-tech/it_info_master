window.onload = function() {
    resultTime();
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
