' VBScript source code

count = 0
currentPath = createobject("Scripting.FileSystemObject").GetFile(Wscript.ScriptFullName).ParentFolder.Path
Set fso=CreateObject("Scripting.FileSystemObject")
Set objPhotoshopApp=CreateObject("Photoshop.Application")

preFileCount = 0
postFileCount = 0

'//jpg
preJpgPath = currentPath & "\process\preJpg"
postJpgPath = currentPath & "\process\postJpg"
'//png
prePngPath = currentPath & "\process\prePng"
postPngPath = currentPath & "\process\postPng"
'Wscript.echo preJpgPath  ����Ի��򣬵�����
 


Call ExecuteCMD()

'//ִ�в���
Sub ExecuteCMD()
    postFileCount = 0
    prePath = preJpgPath
	postPath = postJpgPath
	if count = 1 then
		prePath = prePngPath
		postPath = postPngPath
	end if
	
    Call InitEnv(prePath)
    Call InitPhotoshop(objPhotoshopApp)     '//��ʼ��PS
	
	if count = 1 then
		Call ExecuteBatchPng()  
	else 
		Call ExecuteBatchJpg()  
	end if
	
    Call LoopExecuteFinish(postPath)
End Sub

 
'//��ʼ������
Sub InitEnv(prePath)
	preFileCount = GetFileCount(prePath)
End Sub
 
'//��ʼ��Photoshop���ر������Ѵ򿪵��ĵ�
Sub InitPhotoshop(oPhotoshop)
    Do While oPhotoshop.Documents.Count          
        oPhotoshop.ActiveDocument.Close
    Loop
End Sub 

'//��ȡ�ļ������ļ�����
Function  GetFileCount(path)
	set fs=fso.getfolder(path).files
	for each f in fs
		GetFileCount = GetFileCount + 1
    Next
End Function 

Sub ExecuteBatchJpg()
	'//����PS������������
	Set objShell = CreateObject("Wscript.Shell")
    WScript.Sleep 3000
	objShell.SendKeys "%F"
	objShell.SendKeys "%F"
	objShell.SendKeys "{U}"
	objShell.SendKeys "{B}"
	
	
	'//ѡ�����
	objShell.SendKeys "{TAB}"
	'��Ϣ1S�ٷ��ͷ������������Ч
	WScript.Sleep 1000
	objShell.SendKeys "{DOWN}"
	objShell.SendKeys "{DOWN}"
	
	'//ѡ����
    objShell.SendKeys "{TAB}"
	'��Ϣ1S�ٷ��ͷ������������Ч
	WScript.Sleep 1000
	objShell.SendKeys "{UP}"
	objShell.SendKeys "{UP}"
	
	'//ѡ��·��
	WScript.Sleep 1000
	objShell.SendKeys "%C"
	objShell.SendKeys "preJpg"
    WScript.Sleep 1000
	objShell.SendKeys "{ENTER}"
	
	'//ִ�ж���
	WScript.Sleep 1000
	objShell.SendKeys "{ENTER}"
End Sub

Sub ExecuteBatchPng()
	'//����PS������������
	Set objShell = CreateObject("Wscript.Shell")
    WScript.Sleep 3000
	objShell.SendKeys "%F"
	objShell.SendKeys "%F"
	objShell.SendKeys "{U}"
	objShell.SendKeys "{B}"
	
	'//ѡ������
	objShell.SendKeys "{TAB}"
	'��Ϣ1S�ٷ��ͷ������������Ч
	WScript.Sleep 1000
	objShell.SendKeys "{DOWN}"
	objShell.SendKeys "{DOWN}"

	'//ѡ����	
	objShell.SendKeys "{TAB}"
	'��Ϣ1S�ٷ��ͷ������������Ч
	WScript.Sleep 1000
	objShell.SendKeys "{DOWN}"
	objShell.SendKeys "{DOWN}"
  
    '//ѡ��·��
	WScript.Sleep 1000
	objShell.SendKeys "%C"
	objShell.SendKeys "prePng"
	WScript.Sleep 1000
	objShell.SendKeys "{ENTER}"
	
	'//ִ�ж���
    WScript.Sleep 1000
	objShell.SendKeys "{ENTER}"
End Sub

Sub LoopExecuteFinish(path)
	do 
    	if preFileCount = postFileCount then
			if count = 1 then 
				call ExitPhotoshop(objPhotoshopApp)
				WScript.Sleep 1000
				call InvokeBat()
				Exit do
			else 
				count = count + 1
				call ExecuteCMD()
				Exit do
			end if
		else 
			postFileCount = getFileCount(path)
			WScript.Sleep 1000
		end if
	loop
End Sub


'//�˳�Photoshop
Sub ExitPhotoshop(oPhotoshop)
    oPhotoshop.Quit
End Sub


Sub InvokeBat() 
    Set shell=Wscript.createobject("wscript.shell")
    a=shell.run("ѹ��.bat",0)
End Sub