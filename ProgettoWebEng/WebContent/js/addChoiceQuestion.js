/*
    Per non complicare le funzioni di aggiunta dei campi per le domande questo file si occupera delle domande a scelta radio e checkbox
*/

function addChoiceQuestion(d, s, l, o){
    var choiceQuestion = document.createElement('input');

    var tipo = ['Radio','Checkbox'];
    var selectList = document.createElement('select');
    selectList.id = s;
    selectList.name = 'tipo';
    var field = document.getElementById('formDomande');
    var container = document.createElement('div');
    container.name = 'domandaDiv';
    container.id = 'domandaDiv';
    container.className = 'divDomanda';
    container.className = 'domandaDiv';
    var testo = document.createElement('h4');
    testo.innerHTML = 'Inserisci qui la domanda:';
    var obb = document.createElement('input');
    obb.type = 'radio';
    obb.id = d.concat('obb');
    obb.name = 'obbligatoria';
    obb.value = 'Si';
    var obbLabel = document.createElement('label');
    obbLabel.htmlFor = d.concat('obb');
    obbLabel.innerHTML = "Obbligatoria";
    var obbNo = document.createElement('input');
    obbNo.type = 'radio';
    obbNo.id = d.concat('obbNo');
    obbNo.name = 'obbligatoria';
    obbNo.value = 'No';
    var obbLabelNo = document.createElement('label');
    obbLabelNo.htmlFor = d.concat('obbNo');
    obbLabelNo.innerHTML = "Opzionale";
    testo.innerHTML = 'Inserisci qui la domanda:';
    var input = document.createElement('input');
    input.type = 'text';
    input.name = 'domanda';
    input.id = d;
    input.className = 'textField';
    input.placeholder = 'Domanda';
    var buttone = document.createElement('input');
    buttone.value = 'Aggiungi opzione';
    buttone.id = 'addChoice';
    buttone.type = 'button';
    var nota = document.createElement('input');
    nota.id = l;
    nota.name = 'nota';
    nota.className = 'textField';
    nota.type = 'text'; 
    nota.placeholder = 'Nota';
    container.appendChild(testo);
    container.appendChild(obb);
    container.appendChild(obbLabel);
    container.appendChild(obbNo);
    container.appendChild(obbLabelNo);
    container.appendChild(selectList);
    container.appendChild(buttone);
    var option = document.createElement('input');
    for (var i = 0; i < tipo.length; i++) {
        var option = document.createElement("option");
        option.value = tipo[i];
        option.text = tipo[i];
        option.id = tipo[i];
        selectList.appendChild(option);
    }
    container.appendChild(input);
    buttone.onclick = function() { //addOption(container, d) 
        var id = d + '/opzione.';
        var idem;
        var ele = document.getElementById(id + '1');
        if(!ele){
            if (o == undefined){
                o = 1;
            }
            var option = document.createElement('input');
            option.type = 'text';
            option.id = id + '1';
            option.name = o + 'opzione';
            option.className = 'choiceField';
            option.placeholder = 'Opzione';
            container.appendChild(document.createElement('br'));
            container.appendChild(option);
        } else {
            while(ele){
                var ss = ele.id;
                var str1 = ss.substring(ss.indexOf('.') + 1);
                str1 *= 1;
                str1 += 1;
                idem = id.concat(str1);
                ele = document.getElementById(idem);
            }
            var option = document.createElement('input');
            option.type = 'text';
            option.id = idem;
            option.name = o + 'opzione';
            option.className = 'choiceField';
            option.placeholder = 'Opzione';
            container.appendChild(document.createElement('br'));
            container.appendChild(option);
        }
    };
    container.appendChild(nota);
    field.appendChild(container);
    field.appendChild(document.createElement('br'));
}

function addMultiQuestion(){
    var campo = 'domanda-';
    var selectNumber = 'select-'
    var not = 'nota-'
    var id = campo.concat("1");
    var idSelect = selectNumber.concat('1');
    var idNota = not.concat("1");
    var ele = document.getElementById(id);
    var sel = document.getElementById(idSelect);
    var nota = document.getElementById(idNota);
    if (!ele) {
        addChoiceQuestion(id, idSelect, idNota);
    } else {
        while (ele){
            var ss = ele.id;
            var sss = sel.id;
            var ssss = nota.id;
            var str1 = ss.substring(ss.indexOf("-") + 1);
            var str2 = sss.substring(sss.indexOf("-") + 1);
            var str3 = ssss.substring(ssss.indexOf("-") + 1);
            str1 *= 1;
            str1 += 1;
            str2 *= 1;
            str2 += 1;
            str3 *= 1;
            str3 += 1;
            id = campo.concat(str1);
            idSelect = selectNumber.concat(str2);
            idNota = not.concat(str3);
            ele = document.getElementById(id);
            sel = document.getElementById(idSelect);
            nota = document.getElementById(idNota);
        }
        addChoiceQuestion(id, idSelect, idNota, str1);
    }
}