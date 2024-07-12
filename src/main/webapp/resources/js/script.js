window.onload = function () {

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


    // 타이머 기능 구현
    // variables
    const startButton = document.getElementById('startButton');
    const stopButton = document.getElementById('stopButton');
    const resetButton = document.getElementById('resetButton');

    let timer;
    let duration = parseInt(durationSelect.value, 10);
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
        duration = parseInt(durationSelect.value, 10);
        resetTimer();
    });

    // 초기 타이머 설정
    updateDisplay();
}
