import string,cgi,time
from os import curdir, sep
from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
#import pri

class MyHandler(BaseHTTPRequestHandler):

    def do_GET(self):
        try:
            if self.path.endswith(".html"):
                f = open(curdir + sep + self.path)
                self.send_response(200)
                self.send_header('Content-type',    'text/plain')
                self.end_headers()
                self.wfile.write(f.read())
                f.close()
                return
            elif self.path.endswith(".png"):   #our dynamic content
                f = open(curdir + sep + self.path) #self.path has /test.html
                self.send_response(200)
                self.send_header('Content-type',    'image/png')
                self.end_headers()
                self.wfile.write(f.read())
                f.close()
                return
            elif self.path == '/api/json':
                f = open(curdir + sep + 'fixtures/all_builds.json')
                self.send_response(200)
                self.send_header('Content-type',    'application/json')
                self.end_headers()
                self.wfile.write(f.read())
                f.close()
                return
            if self.path.endswith("marvin"):
                f = open(curdir + sep + 'fixtures/marvin')

                self.send_response(200)
                self.send_header('Content-type',    'application/json')
                self.end_headers()
                self.wfile.write(f.read())
                f.close()
                return
            if self.path.endswith("development"):
                f = open(curdir + sep + 'fixtures/development')

                self.send_response(200)
                self.send_header('Content-type',    'application/json')
                self.end_headers()
                self.wfile.write(f.read())
                f.close()
                return

            if self.path.endswith("gandalf"):
                f = open(curdir + sep + 'fixtures/gandalf')

                self.send_response(200)
                self.send_header('Content-type',    'application/json')
                self.end_headers()
                self.wfile.write(f.read())
                f.close()
                return

            if self.path.endswith("labs"):
                f = open(curdir + sep + 'fixtures/labs')

                self.send_response(200)
                self.send_header('Content-type',    'application/json')
                self.end_headers()
                self.wfile.write(f.read())
                f.close()
                return

            if self.path.endswith("production"):
                f = open(curdir + sep + 'fixtures/production')

                self.send_response(200)
                self.send_header('Content-type',    'application/json')
                self.end_headers()
                self.wfile.write(f.read())
                f.close()
                return

            if self.path.endswith("qa"):
                f = open(curdir + sep + 'fixtures/qa')

                self.send_response(200)
                self.send_header('Content-type',    'application/json')
                self.end_headers()
                self.wfile.write(f.read())
                f.close()
                return

            if self.path.endswith("staging"):
                f = open(curdir + sep + 'fixtures/staging')

                self.send_response(200)
                self.send_header('Content-type',    'application/json')
                self.end_headers()
                self.wfile.write(f.read())
                f.close()
                return

            if self.path.endswith("unit"):
                f = open(curdir + sep + 'fixtures/unit')

                self.send_response(200)
                self.send_header('Content-type',    'application/json')
                self.end_headers()
                self.wfile.write(f.read())
                f.close()
                return



            return

        except IOError:
            self.send_error(404,'File Not Found: %s' % self.path)


    def do_POST(self):
        global rootnode
        try:
            ctype, pdict = cgi.parse_header(self.headers.getheader('content-type'))
            if ctype == 'multipart/form-data':
                query=cgi.parse_multipart(self.rfile, pdict)
            self.send_response(301)

            self.end_headers()
            upfilecontent = query.get('upfile')
            print "filecontent", upfilecontent[0]
            self.wfile.write("<HTML>POST OK.<BR><BR>");
            self.wfile.write(upfilecontent[0]);

        except :
            pass

def main():
    try:
        server = HTTPServer(('', 8900), MyHandler)
        print "Starting Hudson/Jenkins on port 8900\n"
        server.serve_forever()
    except KeyboardInterrupt:
        print '^C received, shutting down server'
        server.socket.close()

if __name__ == '__main__':
    main()

