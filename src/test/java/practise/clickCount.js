const button = $("#btnLogin");
const textarea = $("#txtUserName");
$(button).off();
$(button).click((() => {
    let count = 0;
    return () => textarea.val(`click count ${count++}`);
})());