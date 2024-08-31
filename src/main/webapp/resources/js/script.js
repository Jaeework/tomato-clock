// document.addEventListener('DOMContentLoaded', function() {
window.onload = function () {
    const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;
    const csrfTokenValue = document.querySelector('meta[name="_csrf"]').content;

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

            duration = data.duration;
            remainingTime = duration * 60;
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
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json',
                [csrfHeaderName]: csrfTokenValue,
            },
            body: JSON.stringify(settings),
            credentials: "same-origin",
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
    let isTimerRunning = false; // 타이머가 동작 중인지 체크
    let duration;
    let remainingTime;
    let currentTimerSessionId = document.getElementById('currentTimerSessionId').value;

    function updateDisplay() {
        const minutes = Math.floor(remainingTime / 60);
        const seconds = remainingTime % 60;
        minutesDisplay.textContent = String(minutes).padStart(2, '0');
        secondsDisplay.textContent = String(seconds).padStart(2, '0');
    }

    function startTimer() {
        if (!currentTimerSessionId) {
            createNewTimerSession(); // Create a new session if no current session exists
        }

        if(!isTimerRunning) {
            isTimerRunning = true;
            timer = setInterval(function() {
                if (remainingTime > 0) {
                    remainingTime--;
                    updateDisplay();
                } else {
                    stopTimer(); // Automatically stop when time runs out
                }
            }, 1000);
        }

    }

    function stopTimer() {
        if(isTimerRunning) {
            clearInterval(timer);
            isTimerRunning = false;
            if (currentTimerSessionId) {
                const usedTime = duration * 60 - remainingTime;
                updateTimerSession(usedTime);
            }
        }
    }

    function resetTimer() {
        if(currentTimerSessionId && isTimerRunning) {
            stopTimer();
        }
        remainingTime = duration * 60;
        updateDisplay();
        currentTimerSessionId = null; // Reset current session ID
        document.getElementById('currentTimerSessionId').value = '';
    }

    function createNewTimerSession() {
        fetch('/api/statistics/createTimerSession', {
            method: 'POST',
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json',
                [csrfHeaderName]: csrfTokenValue,
            },
            credentials: "same-origin",
        })
            .then(response => response.text())
            .then(data => {
                currentTimerSessionId = data;
                console.log("created Timer Session Id : ", currentTimerSessionId);
                document.getElementById('currentTimerSessionId').value = currentTimerSessionId;
            })
            .catch(error => console.error('Error creating session:', error));
    }

    function updateTimerSession(usedTime) {
        if (!currentTimerSessionId) return;

        fetch('/api/statistics/updateTimerSession', {
            method: 'POST',
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json',
                [csrfHeaderName]: csrfTokenValue,
            },
            body: JSON.stringify({ sessionId: currentTimerSessionId, usedTime: usedTime }),
            credentials: "same-origin",
        })
            .then(response => response.text())
            .then(data => console.log('Session updated:', data))
            .catch(error => console.error('Error updating session:', error));
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

}
// });
