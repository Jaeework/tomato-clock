// document.addEventListener('DOMContentLoaded', function() {
window.onload = function () {

    // Fetch User Setting
    fetch('/api/settings/get')
        .then(response => response.json())
        .then(data => {
            document.getElementById('duration').value = data.duration;
            document.getElementById('backgroundColor').value = data.bgColor;
            document.getElementById('shadowColor').value = data.shadowColor;
            document.getElementById('textColor').value = data.txtColor;
            document.getElementById('backgroundImage').value = data.bgImgName;

            // 로드된 설정을 적용
            document.documentElement.style.setProperty('--color-primary', data.bgColor);
            document.documentElement.style.setProperty('--color-shadow', data.shadowColor);
            document.documentElement.style.setProperty('--color-timer-font', data.txtColor);

            remainingTime = data.duration * 60;
            updateDisplay();
        })
        .catch((error) => {
            console.error('Error:', error);
        });

    // Setting 기능 구현
    // variables
    const minutesDisplay = document.getElementById('minutes');
    const secondsDisplay = document.getElementById('seconds');
    const durationSelect = document.getElementById('duration');
    const applyDurationButton = document.getElementById('applyDuration');

    // change background color
    document.getElementById('backgroundColor').addEventListener('input', function() {
        document.documentElement.style.setProperty('--color-primary', this.value);
    });

    // change shadow color
    document.getElementById('shadowColor').addEventListener('input', function() {
        document.documentElement.style.setProperty('--color-shadow', this.value);
    });

    // change font color
    document.getElementById('textColor').addEventListener('input', function() {
        document.documentElement.style.setProperty('--color-timer-font', this.value);
    });


    // Save User Setting
    document.getElementById('saveSettings').addEventListener('click', function() {
        const settings = {
            //userId: "userId", // 사용자 ID를 동적으로 설정해야 함
            duration: parseInt(document.getElementById('duration').value),
            txtColor: document.getElementById('textColor').value,
            shadowColor: document.getElementById('shadowColor').value,
            bgColor: document.getElementById('backgroundColor').value,
            bgImgUuid: null,
            bgImgName: null
        };

        fetch('/api/settings/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(settings)
        })
            .then(response => response.text())
            .then(data => {
                if(data === 'success') {
                    alert('Your Setting has been successfully saved.');
                }
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    });


    // 로그아웃 csrf 토큰 전송
    $("#logoutButton").on("click", function (e) {
        e.preventDefault();
        $("#logoutForm").submit();
    });


    // 타이머 기능 구현
    // variables
    const startButton = document.getElementById('startButton');
    const stopButton = document.getElementById('stopButton');
    const resetButton = document.getElementById('resetButton');

    let timer;
    let duration = parseInt(durationSelect.value);
    let remainingTime = duration * 60;

    function updateDisplay() {
        const minutes = Math.floor(remainingTime / 60);
        const seconds = remainingTime % 60;
        minutesDisplay.textContent = String(minutes).padStart(2, '0');
        secondsDisplay.textContent = String(seconds).padStart(2, '0');
    }

    function startTimer() {
        timer = setInterval(function() {
            if (remainingTime > 0) {
                remainingTime--;
                updateDisplay();
            } else {
                clearInterval(timer);
                // 타이머 완료 시 동작 추가
                startButton.style.display = 'inline-block';
                stopButton.style.display = 'none';
            }
        }, 1000);
    }

    function stopTimer() {
        clearInterval(timer);
    }

    function resetTimer() {
        stopTimer();
        remainingTime = duration * 60;
        updateDisplay();
    }

    startButton.addEventListener('click', function() {
        startTimer();
        startButton.style.display = 'none';
        stopButton.style.display = 'inline-block';

    });
    stopButton.addEventListener('click', function() {
        stopTimer();
        startButton.style.display = 'inline-block';
        stopButton.style.display = 'none';
    });
    resetButton.addEventListener('click', function() {
        resetTimer();
        startButton.style.display = 'inline-block';
        stopButton.style.display = 'none';
    });
    // change duration
    applyDurationButton.addEventListener('click', function () {
        duration = parseInt(durationSelect.value);
        resetTimer();
    });

    // 초기 타이머 설정
    // updateDisplay();
}
// });
