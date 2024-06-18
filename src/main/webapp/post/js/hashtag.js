var inputValueForFocus = "";
const maxTags = 5;

function makeInputTag(val) {
    let tag = '<span class="inp_tag"> <span>#</span>' +
        '<input type="text" name="tagText" id="tagText" placeholder="태그입력" class="tf_g" ' +
        'value="' + val + '" style="box-sizing: content-box; width: 52px;"/> </span>';
    return tag;
}

function makeTextTag(val) {
    let tag = '<span class="txt_tag" data-tag="' + val + '"># ' + val + '<i class="fas fa-times delete-tag"></i></span>';
    return tag;
}

function adjustWidth(element, text) {
    let test = $('<span>').html(text).css({
        'font-size': $(element).css('font-size'),
        'font-family': $(element).css('font-family'),
        'white-space': 'pre'
    });
    test.appendTo('body');
    let width = test.width();
    test.remove();
    $(element).css('width', width + 'px');
}

function replaceFromInputTag() {
    const inputTag = $("#tagText");
    let tagValue = inputTag.val().trim();

    if (tagValue.length > 0) {
        if ($('.txt_tag[data-tag="' + tagValue + '"]').length > 0) {
            inputTag.val('');
            return;
        }

        if ($('.txt_tag').length >= maxTags) {
            inputTag.val('');
            return;
        }

        $(inputTag).closest('.inp_tag').replaceWith(makeTextTag(tagValue));

        if ($('.inp_tag').length === 0 && $('.txt_tag').length < maxTags) {
            $('.editor_tag').append(makeInputTag(""));
        }
    } else {
        inputTag.css('width', "52px");
        inputTag.attr('placeholder', '태그입력');
    }
}

$(document).ready(function () {
    $('.editor_tag').append(makeInputTag(""));
});

$(document).on('input', '#tagText', function () {
    let text = $(this).val();
    if (text.length > 0) {
        adjustWidth(this, text);
    } else {
        $(this).css('width', '52px');
    }
});

$(document).on('keyup blur', '#tagText', function (e) {
    if (e.type === 'keyup' && e.key !== 'Enter') return;
    replaceFromInputTag();
});

$(document).on('click', '.txt_tag', function (e) {
    if ($(e.target).hasClass('delete-tag')) return;
    e.preventDefault();
    let tagValue = $(this).data('tag');
    $(this).replaceWith(makeInputTag(tagValue));
    let input = $('#tagText');
    adjustWidth(input, tagValue);
    input.focus();
    input[0].setSelectionRange(tagValue.length, tagValue.length);
    inputValueForFocus = input.val();
});

$(document).on('click', '.delete-tag', function (e) {
    $(this).closest('.txt_tag').remove();
    if ($('.inp_tag').length === 0 && $('.txt_tag').length < maxTags) {
        $('.editor_tag').append(makeInputTag(""));
    }
    updateTagList();
});

$(document).on('blur', '#tagText', function () {
    let tagValue = $(this).val();
    if (tagValue.length === 0) {
        $(this).val(inputValueForFocus);
        replaceFromInputTag();
    }
});