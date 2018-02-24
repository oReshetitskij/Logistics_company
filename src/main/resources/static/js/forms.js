function validate(input) {
    if (input.value !== document.getElementById("password").value) {
        input.setCustomValidity("Passwords don't match");
    } else {
        input.setCustomValidity("");
    }
}

function createMultiSelect(selector) {
    var el = $(selector);

    $(document).ready(function () {
        el.multiselect({
            numberDisplayed: 3,
            delimiterText: ", ",
            allSelectedText: false,
            onChange: function (option, checked, select) {
                var values = [];
                el.find("option").each(function () {
                    values.push(this.selected);
                });
                var noneSelected = values.every(function (val) {
                    return val === false;
                });
                if (noneSelected) {
                    el.multiselect("select", option.val());
                }
            },
            onInitialized: function (select, container) {
                setTimeout(function () {
                    var values = [];
                    el.find("option").each(function () {
                        values.push(this.selected);
                    });
                    var noneSelected = values.every(function (val) {
                        return val === false;
                    });
                    if (noneSelected) {
                        var firstValue = el.find("option").first().val();
                        el.multiselect("select", firstValue);
                    }
                }, 0);
            }
        });
    });
}