/**
 *  @author trungNV (trungnv.bks@gmail.com)
 * @since 18-08-2023
 */
$(document).ready(function () {
    initDataTable();
    formSubmit();
    onSearch();

    $("#btnCloseModal").click(function () {
        onCloseModal();
    })
})

/* VARIABLE */
const messageError = {
    emptyUsername: "u_nm_empt", // Username can't be blank
    emptyUserId: "u_id_empt", // UserId can't be blank
    emptyPassword: 'pw_empt', // Password can't be blank
    confirmPasswordNotCorrect: 'cf_pw_icr',
    userIdExist: "dupmsg",
    usernameSpecialCharacter: "Username should not contain special characters",
    PasswordSpecialCharacter: "Password should not contain special characters",
    PasswordMinError: "The password must be at least 4 characters long.",
    userIdSpecialCharacter: "UserId should not contain special characters",
}

/* FUNCTION */
/**
 * This function initializes a DataTable with specific configurations and retrieves data from a server endpoint to populate the table.
 */
function initDataTable() {
    $("#tblUser").removeAttr("width").DataTable({
        processing: false,
        serverSide: false, // Paging handle by the server side
        paging: true,
        searching: true,
        lengthChange: false,
        lengthMenu: [20, 40, 60],
        bInfo: false,
        ordering: false,
        autoWidth: true,
        language: {
            emptyTable: $.localeMessage('tb_empt_tx'),
            paginate: {
                "next": nextButton(),
                "previous" : previousButton()
            }
        },
        ajax: {
            url: "/api/user-management/list", type: "GET", cache: false
        }, columnDefs: [
            {
                targets: [1, 2, 3, 4],
                className: 'dt-body-left'
            }
        ],
        columns: [
            {data: "seqNo"},
            {data: "userNm", className:"pl-65"},
            {
                data: "userId", render: function (data, type, full, meta) {
                    return "<a href=\"#\" data-toggle=\"modal\" data-target=\"#modalUser\" class='userDetail text-decoration-underline' onclick=\"showModal('edit', " + full.seqNo + ", '" + full.userId + "' )\">" + data + "</a>";
                }
            },
            {data: "authTyNm"},
            {
                data: "regDt", render(data, type, full, meta) {
                    let time = "";
                    if (full && full.regDt) {
                        time = full.regDt.substring(0, 16);
                    }
                    return "<div>" + time + "</div>"
                }
            }
        ]
    });
}

/**
 * This function is used to handle the form submission and validation for a user form.
 */
function formSubmit() {

    // Button for dialog duplicate
    const buttonsDuplicate = [{
        text: $.localeMessage("confirm"), class: 'btn btn-outline-secondary btn-sm text-uppercase', click: function () {
            closeDialog();
        }
    }]

    // Store errors message key
    let errors = [];

    // Flag check duplicate userId
    let isDuplicate = null;


    // Handle when clicked button save;
    $('#btnSubmit').click(async function (e) {
        e.preventDefault();
        /* get variable */
        const modal = $("#modalUser");
        const modalType = modal.attr('modal-type');
        const userSeq = modal.attr('user-seq');
        const username = $("#username").val();
        const userId = $("#userId").val();
        const oldUserId = $("#modalUser").attr('old-userid');
        const password = $("#password").val();
        const confirmPassword = $('#confirmPassword').val();
        const authority = $("#authority").val();
        /* validate */
        if (!username || username === "") {
            errors.push($.localeMessage(messageError.emptyUsername));
        }
        if (!userId || userId === "") {
            errors.push($.localeMessage(messageError.emptyUserId));
        }
        // case create check password null, blank;
        if (modalType === 'add' && (!password || password === "")) {
            errors.push($.localeMessage(messageError.emptyPassword));
        }
        if (modalType === 'add' && (!confirmPassword || confirmPassword === "" || confirmPassword !== password)) {

            errors.push($.localeMessage(messageError.confirmPasswordNotCorrect));
        }
        // case edit
        if (modalType === 'edit' && (password !== confirmPassword || (password === "" && confirmPassword !== "") || (password !== "" && confirmPassword === ""))) {
            errors.push($.localeMessage(messageError.confirmPasswordNotCorrect));
        }

        // Validate Special Character
        var inputPassword = document.getElementById("password").value;
        if (!validatePassword(inputPassword)) {
            errors.push($.localeMessage(messageError.PasswordSpecialCharacter));
        }
        if (!validatePasswordLength(inputPassword)) {
            errors.push($.localeMessage(messageError.PasswordMinError));
        }

        var inputUsername = document.getElementById("username").value;
        if (!validateUsername(inputUsername)) {
            errors.push($.localeMessage(messageError.usernameSpecialCharacter));
        }

        var inputUserId = document.getElementById("userId").value;
        if (!validateUserId(inputUserId)) {
            errors.push($.localeMessage(messageError.userIdSpecialCharacter));
        }

        // If error show dialog
        if (errors.length > 0) {
            // show errors
            let dialogBody = "<div>";
            errors.forEach(function (item) {
                if (item) {
                    dialogBody += `<p class="${errors.length > 1 ? 'text-left' : 'text-center'}">${item}</p>`
                }
            })
            dialogBody += "</div>";

            function onClose() {
                errors = [];
            }

            showDialog($.localeMessage('warning'), dialogBody, [], onClose, true);
        } else {
            // modal type equal 'edit', skip check duplicate userId;
            if (modalType === 'edit' && oldUserId === userId) {
                isDuplicate = false;
            }
            //if duplicate is null => call func check duplicate
            if (isDuplicate === null) {
                const preCheckDuplicate = await checkDulicateUserId(userId);
                if(preCheckDuplicate){
                    onDuplicateTrue($("#userId"));
                    showDialog($.localeMessage('duplicate'), $.localeMessage(messageError.userIdExist), buttonsDuplicate, null, false);
                    return;
                }
                // if duplicate === true => break
            } else if (isDuplicate === true){
                onDuplicateTrue($("#userId"));
                showDialog($.localeMessage('duplicate') , $.localeMessage(messageError.userIdExist), buttonsDuplicate, null, false);
                return;
            }

            //     Call api submit
            if (modalType === 'add') {
                $.ajax({
                    url: '/api/user-management', method: 'POST',
                    data: JSON.stringify({
                        userNm: username, userId: userId, userPw: password, authSeqNo: authority,
                    }),
                    contentType: "application/json",
                    dataType: "json",
                    success: function (res) {
                        if (res.success) {
                            // show dialog after create successful
                            const buttons = [{
                                text: $.localeMessage('confirm'),
                                class: 'btn btn-outline-secondary btn-sm text-uppercase',
                                click: function () {
                                    onCloseModal();
                                    closeDialog();
                                    reloadDatatable();
                                }
                            }]
                            showDialog($.localeMessage('result'), $.localeMessage('save_cplt'), buttons, null, false);
                        }
                    },
                    error: function () {
                        showDialog($.localeMessage('error'), $.localeMessage('sth_w_w'), [], null, true);
                    }
                })
            } else {
                $.ajax({
                    url: '/api/user-management', method: 'PUT',
                    data: JSON.stringify({
                        seqNo: userSeq, userNm: username, userId: userId, userPw: password, authSeqNo: authority,
                    }),
                    contentType: "application/json",
                    dataType: "json",
                    success: function (res) {
                        if (res.success) {
                            // show dialog when update successful
                            const buttons = [{
                                text: $.localeMessage('confirm'),
                                class: 'btn btn-outline-secondary btn-sm',
                                click: function () {
                                    onCloseModal();
                                    reloadDatatable();
                                    closeDialog();
                                }
                            }]
                            showDialog($.localeMessage('result'), $.localeMessage('save_cplt'), buttons, null, false);
                        }
                    },
                    error: function () {
                        showDialog($.localeMessage('error'), $.localeMessage('sth_w_w'), [], null, true);
                    }
                })
            }

        }
    })

    // reset when change user Id value
    $("#userId").keyup(function () {
        isDuplicate = null;
        clearDuplicateStyle($("#userId"));
    })

    // reset when change user Name value
    $("#username").keyup(function () {
        clearDuplicateStyle($("#username"));
    })

    // reset when change Password value
    $("#password").keyup(function () {
        clearDuplicateStyle($("#password"));
    })

    // handle when click duplicate check button
    $("#duplicateCheckBtn").click(async function (e) {
        const modal = $("#modalUser");
        const userId = $("#userId").val();
        const duplicate = await checkDulicateUserId(userId);

        if (!validateUserId(userId)) {
            errors.push($.localeMessage(messageError.userIdSpecialCharacter));
        }

        if (duplicate) {
            isDuplicate = true;
            onDuplicateTrue($("#userId"));
            errors.push($.localeMessage(messageError.userIdExist));
        } else {
            onDuplicateFalse($("#userId"))
            isDuplicate = false;
        }

        // If error show dialog
        if (errors.length > 0) {
            onDuplicateTrue($("#userId"));
            // show errors
            let dialogBody = "<div>";
            errors.forEach(function (item) {
                if (item) {
                    dialogBody += `<p class="${errors.length > 1 ? 'text-left' : 'text-center'}">${item}</p>`
                }
            })
            dialogBody += "</div>";

            function onClose() {
                errors = [];
            }

            console.log(errors);
            showDialog($.localeMessage('duplicate'), dialogBody, [], onClose, true);
        }
    })

    /**
     * Checks if the userId already exists or not
     * @param userId
     * @returns {Promise<boolean>}
     */
    async function checkDulicateUserId(userId) {
        let isDuplicate = true;
        try {
            const checkResult = await $.ajax({
                url: '/api/user-management/check-duplicate-user-management-id/' + userId,
            })
            if (checkResult.success) {
                isDuplicate = true;
            } else {
                isDuplicate = false;
            }
        } catch (e) {
            isDuplicate = false;
        }

        return isDuplicate
    }

    /**
     * Validate Type Input
     */

    function validateUsername(input) {
        var regex = /^[\p{Script=Latin}\p{Script=Bengali}\p{Script=Hangul}_0-9]*$/gu;
        if (regex.test(input) == true) {
            if (input != "") {
                onDuplicateFalse($("#username"));
            }
            return true;
        } else if (regex.test(input) == false) {
            onDuplicateTrue($("#username"));
            return false;
        }
    }

    function validateUserId(input) {
        var regex = /^[a-zA-Z0-9_]*$/gu;
        if (regex.test(input)) {
            if (input != "") {
                onDuplicateFalse($("#userId"));
            }
            return true;
        } else {
            onDuplicateTrue($("#userId"));
            return false;
        }
    }

    function validatePassword(input) {
        var regex = /^[a-zA-Z0-9_!@#$%^&*()]*$/gu;
        if (regex.test(input)) {
            if (input != "") {
                onDuplicateFalse($("#password"));
            }
            return true;
        } else {
            onDuplicateTrue($("#password"));
            return false;
        }
    }

    function validatePasswordLength(input){
        if (input.length < 4){
            onDuplicateTrue($("#password"));
            return false;
        }
        else {
            onDuplicateFalse($("#password"));
            return  true;
        }
    }
}

/**
 * This function is used to show a modal window with user details.
 * @param type - add | edit
 * @param userSeqNo
 * @param targetUserId
 * */
async function showModal(type, userSeqNo, targetUserId) {

    // clear style check duplicate
    clearDuplicateStyle($("#username"));
    clearDuplicateStyle($("#userId"));
    clearDuplicateStyle($("#password"));

    const modal = $("#modalUser");
    const btnSubmit = $("#btnSubmit");
    modal.attr("modal-type", type);
    // show text button submit
    $(".rightAction").html("");
    if (type === "edit") {
        btnSubmit.text($.localeMessage('save'));
    } else {
        $("#form")[0].reset();
        btnSubmit.text($.localeMessage('save'));
    }
    if (type === "edit" && userSeqNo) {
        modal.attr("user-seq", userSeqNo);
        modal.attr('old-userid', targetUserId);
        // hide button check duplicate in detail modal
        $("#duplicateCheckBtn").hide();
        // show button delete when view detail
        const deleteButton = `<button type='button' class="btn btn-sm font-weight-bold" style="width: 100px; background-color: #FD443D; color: #fff" onClick="deleteUser(${userSeqNo})">${$.localeMessage('delete')}</button>`;
        // append delete button
        $(".rightAction").html(deleteButton);
        // fill data to input
        await $.ajax({
            url: '/api/user-management/detail/' + userSeqNo, success: function (res) {
                const data = res.data;
                const {authSeqNo, seqNo, userId, userPw, userNm} = data;
                // set value to input form
                $("#username").val(userNm);
                $("#userId").val(userId);
                $("#password").val(userPw);
                $("#confirmPassword").val(userPw);
                $("#authority").val(authSeqNo);
                // disable userId input when edit
                $("#userId").attr("readonly", true);
                $("#userId").attr("disabled", true);

                modal.modal("show");

            }, error: function () {
                showDialog($.localeMessage('error'), $.localeMessage('sth_w_w'), [], null, true);
            }
        })
    } else {
        const close = ` <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                             <span aria-hidden="true">&times;</span></button>`;
        $(".rightAction").html(close);
        // remove attribute in userId input if its was added before
        $("#duplicateCheckBtn").show();
        $("#userId").removeAttr('readonly');
        $("#userId").removeAttr('disabled');
        modal.modal("show");
    }
}

/**
 * This function is used to handle the closing of a modal window.
 */
function onCloseModal() {
    const modal = $("#modalUser");
    modal.attr("modal-type", "");
    modal.attr("userid", '');
    modal.attr("user-seq", "");
    modal.attr('old-userid', "");
    $(".rightAction").html("");
    modal.modal('hide');

    // clear all value in form
    $("#form")[0].reset();
    // clear style check duplicate
    clearDuplicateStyle($("#username"));
    clearDuplicateStyle($("#userId"));
    clearDuplicateStyle($("#password"));
}

/**
 * This function is used to delete a user from the system.
 * @param userId
 */
function deleteUser(userId) {
    const buttons = [{
        text: $.localeMessage('yes'), class: 'btn btn-outline-secondary btn-sm', click: function () {
            $.ajax({
                url: '/api/user-management/' + userId, method: 'DELETE', success: function (res) {
                    if (res.success) {
                        showDialog($.localeMessage('result'), $.localeMessage('del_u_ss'), [], null, true)
                    } else {
                        showDialog($.localeMessage('result'), $.localeMessage("del_ms"), [], null, true)
                    }
                    onCloseModal();
                    reloadDatatable();
                }, error: function () {
                    showDialog($.localeMessage('error'), $.localeMessage("cant_del_u"), [], null, true);
                }
            })
        }
    }, {
        text: $.localeMessage('no'), class: 'btn btn-outline-secondary btn-sm', click: function () {
            closeDialog();
        }
    }]
    showDialog($.localeMessage('del_item'), $.localeMessage('chk_YN_del'), buttons, onCloseModal, false);
}

/**
 * This function handles the search filter
 */
function onSearch() {
    $(".btnSearch").click(function () {
        const searchUserName = $("#searchUserName");
        const searchUserId = $("#searchUserId");
        $("#tblUser").DataTable().column(1).search(searchUserName.val().trim()).column(2).search(searchUserId.val().trim()).draw();
    })
}

var inputSearchUserName = document.getElementById("searchUserName");
inputSearchUserName.addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        document.getElementById("btnSearch").click();
    }
});

var inputSearchUserId = document.getElementById("searchUserId");
inputSearchUserId.addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        document.getElementById("btnSearch").click();
    }
});

function reloadDatatable() {
    $("#tblUser").DataTable().ajax.reload();
}
