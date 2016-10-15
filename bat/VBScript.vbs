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
'Wscript.echo preJpgPath  输出对话框，调试用
 


Call ExecuteCMD()

'//执行步骤
Sub ExecuteCMD()
    postFileCount = 0
    prePath = preJpgPath
	postPath = postJpgPath
	if count = 1 then
		prePath = prePngPath
		postPath = postPngPath
	end if
	
    Call InitEnv(prePath)
    Call InitPhotoshop(objPhotoshopApp)     '//初始化PS
	
	if count = 1 then
		Call ExecuteBatchPng()  
	else 
		Call ExecuteBatchJpg()  
	end if
	
    Call LoopExecuteFinish(postPath)
End Sub

 
'//初始化环境
Sub InitEnv(prePath)
	preFileCount = GetFileCount(prePath)
End Sub
 
'//初始化Photoshop，关闭所有已打开的文档
Sub InitPhotoshop(oPhotoshop)
    Do While oPhotoshop.Documents.Count          
        oPhotoshop.ActiveDocument.Close
    Loop
End Sub 

'//获取文件夹内文件个数
Function  GetFileCount(path)
	set fs=fso.getfolder(path).files
	for each f in fs
		GetFileCount = GetFileCount + 1
    Next
End Function 

Sub ExecuteBatchJpg()
	'//启动PS的批处理命令
	Set objShell = CreateObject("Wscript.Shell")
    WScript.Sleep 3000
	objShell.SendKeys "%F"
	objShell.SendKeys "%F"
	objShell.SendKeys "{U}"
	objShell.SendKeys "{B}"
	
	
	'//选择分组
	objShell.SendKeys "{TAB}"
	'休息1S再发送方向键，否则不生效
	WScript.Sleep 1000
	objShell.SendKeys "{DOWN}"
	objShell.SendKeys "{DOWN}"
	
	'//选择动作
    objShell.SendKeys "{TAB}"
	'休息1S再发送方向键，否则不生效
	WScript.Sleep 1000
	objShell.SendKeys "{UP}"
	objShell.SendKeys "{UP}"
	
	'//选择路径
	WScript.Sleep 1000
	objShell.SendKeys "%C"
	objShell.SendKeys "preJpg"
    WScript.Sleep 1000
	objShell.SendKeys "{ENTER}"
	
	'//执行动作
	WScript.Sleep 1000
	objShell.SendKeys "{ENTER}"
End Sub

Sub ExecuteBatchPng()
	'//启动PS的批处理命令
	Set objShell = CreateObject("Wscript.Shell")
    WScript.Sleep 3000
	objShell.SendKeys "%F"
	objShell.SendKeys "%F"
	objShell.SendKeys "{U}"
	objShell.SendKeys "{B}"
	
	'//选择动作组
	objShell.SendKeys "{TAB}"
	'休息1S再发送方向键，否则不生效
	WScript.Sleep 1000
	objShell.SendKeys "{DOWN}"
	objShell.SendKeys "{DOWN}"

	'//选择动作	
	objShell.SendKeys "{TAB}"
	'休息1S再发送方向键，否则不生效
	WScript.Sleep 1000
	objShell.SendKeys "{DOWN}"
	objShell.SendKeys "{DOWN}"
  
    '//选择路径
	WScript.Sleep 1000
	objShell.SendKeys "%C"
	objShell.SendKeys "prePng"
	WScript.Sleep 1000
	objShell.SendKeys "{ENTER}"
	
	'//执行动作
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


'//退出Photoshop
Sub ExitPhotoshop(oPhotoshop)
    oPhotoshop.Quit
End Sub


Sub InvokeBat() 
    Set shell=Wscript.createobject("wscript.shell")
    a=shell.run("压缩.bat",0)
End Sub