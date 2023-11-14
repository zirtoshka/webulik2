export function showToast(message) {
    const toast = document.getElementById("custom-toast");
    const toastContent = toast.querySelector(".toast-content");

    toastContent.textContent = message;
    toast.classList.add("show");

    setTimeout(() => {
        toast.classList.remove("show");
    }, 3000); // Скрывать уведомление через 3 секунды
}


